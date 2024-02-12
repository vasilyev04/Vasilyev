package com.vasilyev.movielistapp.presentation.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telecom.Call.Details
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.vasilyev.movielistapp.R
import com.vasilyev.movielistapp.databinding.ActivityFavoriteBinding
import com.vasilyev.movielistapp.presentation.BaseActivity
import com.vasilyev.movielistapp.presentation.details.DetailsActivity
import com.vasilyev.movielistapp.presentation.favorite.adapter.FavoritesListAdapter
import com.vasilyev.movielistapp.presentation.main.MainActivity

class FavoriteActivity : BaseActivity() {
    private var _binding: ActivityFavoriteBinding? = null
    private val binding: ActivityFavoriteBinding
        get() = _binding ?: throw RuntimeException("ActivityFavoriteBinding is null")


    private val viewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(application)
    }

    private val adapter by lazy {
        FavoritesListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createCustomActionBar(ContextCompat.getString(this, R.string.favorites))

        attachRecyclerView()
        observeViewModel()
        setListeners()
    }

    private fun setListeners(){
        binding.btnPopular.setOnClickListener {
            val intent = MainActivity.newMainActivityIntent(this)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun attachRecyclerView(){
        binding.rvMovieList.adapter = adapter

        with(adapter){
            onItemClickCallBack = { id ->
                val intent = if(isNetworkAvailable(this@FavoriteActivity)){
                    DetailsActivity.newIntentShowDetailsOnline(this@FavoriteActivity, id)
                }else{
                    DetailsActivity.newIntentShowDetailsOffline(this@FavoriteActivity, id)
                }
                startActivity(intent)
            }

            onItemLongClickCallBack = { movieItem ->
                viewModel.deleteMovie(movieItem.id)
            }
        }
    }

    private fun observeViewModel(){
        viewModel.movieList.observe(this){
            adapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        fun newIntentFavoritesList(context: Context): Intent
            =  Intent(context, FavoriteActivity::class.java)
    }
}