package com.vasilyev.movielistapp.presentation.details

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.vasilyev.movielistapp.R
import com.vasilyev.movielistapp.databinding.ActivityDetailsBinding
import com.vasilyev.movielistapp.domain.model.MovieItem
import java.lang.RuntimeException

class DetailsActivity : AppCompatActivity() {
    private var _binding: ActivityDetailsBinding? = null
    private val binding: ActivityDetailsBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

    private val viewModel: DetailsViewModel by viewModels(){
        DetailsViewModelFactory(application)
    }

    private var movieItemId: Int = MovieItem.UNDEFINED_ID

    private fun parseIntent(){
        if(!intent.hasExtra(EXTRA_MOVIE_ITEM_ID)) throw RuntimeException("Param movie id is absent")

        movieItemId = intent.getIntExtra(EXTRA_MOVIE_ITEM_ID, MovieItem.UNDEFINED_ID)

        if(!intent.hasExtra(EXTRA_NETWORK_MODE)){
            throw RuntimeException("Param network mode is absent")
        }else{
            val networkMode = intent.getStringExtra(EXTRA_NETWORK_MODE)
            if(networkMode == NETWORK_MODE_ONLINE){
                getMovieOnline(movieItemId)
            }else{
                getMovieOffline(movieItemId)
            }
        }

    }

    private fun getMovieOnline(id: Int){
        viewModel.getMovieItemOnline(id)
    }

    private fun getMovieOffline(id: Int){
        viewModel.getMovieItemOffline(id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        disableActionBar()
        parseIntent()
        observeViewModel()
        setListeners()
    }

    private fun setListeners(){
        binding.btnBack.setOnClickListener{
            finish()
        }
    }

    private fun observeViewModel(){
        viewModel.movieItem.observe(this){ movieItem ->
            fillViews(movieItem)
        }
    }

    private fun getPlaceHolder(): Drawable{
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = PLACE_HOLDER_STROKE_WIDTH
        circularProgressDrawable.centerRadius = PLACE_HOLDER_RADIUS
        circularProgressDrawable.start()
        circularProgressDrawable.setColorSchemeColors(getColor(R.color.main_color))

        return circularProgressDrawable
    }

    private fun fillViews(movieItem: MovieItem){
        Glide
            .with(this)
            .load(movieItem.posterUrl)
            .placeholder(getPlaceHolder())
            .into(binding.ivPoster)



        with(binding){
            ivTitle.text = movieItem.title
            tvDescription.text = movieItem.description
            tvGenres.text = formatGenres(movieItem.genres)
            tvCountries.text = formatCountries(movieItem.countries)
        }

        Log.d("MyMovieItem", movieItem.toString())
    }

    private fun formatGenres(genres: List<String>): SpannableString{
        var text = ContextCompat.getString(this@DetailsActivity, R.string.genres) + ": "

        return spanList(genres, text)
    }

    private fun formatCountries(countries: List<String>): SpannableString{
        var text = ContextCompat.getString(this@DetailsActivity, R.string.countries) + ": "

        return spanList(countries, text)
    }

    private fun spanList(list: List<String>, startWith: String): SpannableString{
        var text = startWith
        val spanEnd = startWith.length

        for (i in list.indices){
            if(i != list.size - 1){
                text += "${list[i]}, "
            }else{
                text += list[i]
            }
        }

        val spannableString = SpannableString(text)
        spannableString.setSpan(
            StyleSpan(Typeface.BOLD),
            0, spanEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        return spannableString
    }

    private fun disableActionBar(){
        supportActionBar?.hide()
    }

    companion object{
        private const val EXTRA_MOVIE_ITEM_ID = "movie_item_id"
        private const val PLACE_HOLDER_RADIUS = 40f
        private const val PLACE_HOLDER_STROKE_WIDTH = 10f
        private const val EXTRA_NETWORK_MODE = "network_mode"
        private const val NETWORK_MODE_ONLINE = "mode_online"
        private const val NETWORK_MODE_OFFLINE = "mode_offline"

        fun newIntentShowDetailsOnline(context: Context, id: Int): Intent{
            val intent = Intent(context, DetailsActivity::class.java)
            with(intent){
                putExtra(EXTRA_MOVIE_ITEM_ID, id)
                putExtra(EXTRA_NETWORK_MODE, NETWORK_MODE_ONLINE)
            }

            return intent
        }

        fun newIntentShowDetailsOffline(context: Context, id: Int): Intent{
            val intent = Intent(context, DetailsActivity::class.java)
            with(intent){
                putExtra(EXTRA_MOVIE_ITEM_ID, id)
                putExtra(EXTRA_NETWORK_MODE, NETWORK_MODE_OFFLINE)
            }

            return intent
        }
    }
}