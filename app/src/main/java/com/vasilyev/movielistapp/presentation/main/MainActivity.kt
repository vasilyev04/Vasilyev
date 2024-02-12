package com.vasilyev.movielistapp.presentation.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.vasilyev.movielistapp.R
import com.vasilyev.movielistapp.databinding.ActivityMainBinding
import com.vasilyev.movielistapp.domain.model.MovieItem
import com.vasilyev.movielistapp.presentation.BaseActivity
import com.vasilyev.movielistapp.presentation.details.DetailsActivity
import com.vasilyev.movielistapp.presentation.favorite.FavoriteActivity
import com.vasilyev.movielistapp.presentation.main.adapter.MovieListAdapter
import java.lang.RuntimeException

class MainActivity : BaseActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding ?: throw RuntimeException("ActivityMainBinding is null")

    private val viewModel: MainViewModel by viewModels(){
        MainViewModelFactory(application)
    }

    private val adapter by lazy {
        MovieListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createCustomActionBar(ContextCompat.getString(this, R.string.popular))

        attachRecyclerView()
        observeViewModel()
        setListeners()
        launchInRightNetworkMode()
    }

    private fun launchInRightNetworkMode(){
        if(isNetworkAvailable(this)){
            hideNetworkError()
            viewModel.getMovieList()
        }else{
            showNetworkError()
        }
    }

    private fun showNetworkError(){
        binding.rvMovieList.visibility = View.GONE
        binding.ivNoConnection.visibility = View.VISIBLE
        binding.tvConnectionError.visibility = View.VISIBLE
        binding.btnRetry.visibility = View.VISIBLE
    }

    private fun hideNetworkError(){
        binding.rvMovieList.visibility = View.VISIBLE
        binding.ivNoConnection.visibility = View.GONE
        binding.tvConnectionError.visibility = View.GONE
        binding.btnRetry.visibility = View.GONE
    }

    private fun setListeners(){
        binding.btnFavorites.setOnClickListener {
            val intent = FavoriteActivity.newIntentFavoritesList(this)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.btnRetry.setOnClickListener {
            launchInRightNetworkMode()
        }

        binding.btnPopular.setOnClickListener {
            launchInRightNetworkMode()
        }
    }

    private fun observeViewModel(){
        viewModel.movieList.observe(this){
            adapter.submitList(it)
        }

        viewModel.isLoading.observe(this){isLoading ->
            if(isLoading){
                showProgressBar()
            }else{
                hideProgressBar()
            }
        }

        viewModel.loadingFailed.observe(this){
            Toast.makeText(this@MainActivity, "Connection Failed", Toast.LENGTH_SHORT).show()
        }

        viewModel.favoriteMovieItem.observe(this){
            viewModel.addMovieItem(it)
        }
    }

    private fun showProgressBar(){
        binding.rvMovieList.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.rvMovieList.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun attachRecyclerView(){
        binding.rvMovieList.adapter = adapter

        with(adapter){
            onItemClickCallBack = { id ->
                if(isNetworkAvailable(this@MainActivity)) {
                    val intent = DetailsActivity.newIntentShowDetailsOnline(this@MainActivity, id)
                    startActivity(intent)
                }
            }

            onItemLongClickCallBack = { movieItem ->
                if(isNetworkAvailable(this@MainActivity)){
                    viewModel.changeFavoriteStatus(movieItem)
                    notifyAdapterItemChanged(movieItem)
                }
            }
        }
    }

    private fun notifyAdapterItemChanged(movieItem: MovieItem){
        val itemIndex = adapter.currentList.indexOfFirst { it.id == movieItem.id }
        val item = adapter.currentList[itemIndex]
        item.isFavorite = !movieItem.isFavorite
        adapter.notifyItemChanged(itemIndex, item)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        fun newMainActivityIntent(context: Context): Intent{
            return Intent(context, MainActivity::class.java)
        }
    }
}