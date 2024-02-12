package com.vasilyev.movielistapp.presentation.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasilyev.movielistapp.data.repository.MovieRepositoryImpl
import com.vasilyev.movielistapp.data.repository.OfflineMovieRepositoryImpl
import com.vasilyev.movielistapp.domain.model.MovieItem
import com.vasilyev.movielistapp.domain.usecase.AddMovieItemUseCase
import com.vasilyev.movielistapp.domain.usecase.DeleteMovieItemUseCase
import com.vasilyev.movielistapp.domain.usecase.GetMovieItemUseCase
import com.vasilyev.movielistapp.domain.usecase.GetMovieListUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {
    private val repository = MovieRepositoryImpl(application)
    private val offlineRepository = OfflineMovieRepositoryImpl(application)

    private val _movieList: MutableLiveData<List<MovieItem>> = MutableLiveData()
    val movieList: LiveData<List<MovieItem>>
        get() = _movieList

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _loadingFailed: MutableLiveData<Any> = MutableLiveData()
    val loadingFailed: LiveData<Any>
        get() = _loadingFailed

    private val _favoriteMoveItem: MutableLiveData<MovieItem> = MutableLiveData()
    val favoriteMovieItem: LiveData<MovieItem>
        get() = _favoriteMoveItem

    private val getMovieListUseCase = GetMovieListUseCase(repository)
    private val getMovieItemUseCase = GetMovieItemUseCase(repository)
    private val addMovieItemUseCase = AddMovieItemUseCase(offlineRepository)
    private val deleteMovieItemUseCase = DeleteMovieItemUseCase(offlineRepository)

    fun getMovieList(){
        startLoading()
        viewModelScope.launch {
            _movieList.value = getMovieListUseCase.invoke()
            stopLoading()
        }
    }

    private fun addToFavorite(id: Int){
        viewModelScope.launch {
            val movieItem = getMovieItemUseCase.invoke(id)
            Log.d("Detailed", movieItem.toString())
            movieItem.isFavorite = true
            _favoriteMoveItem.value = movieItem

            addMovieItem(movieItem)
        }
    }

    fun changeFavoriteStatus(movieItem: MovieItem){
        if(!movieItem.isFavorite){
            addToFavorite(movieItem.id)
        }else{
            deleteFromFavorites(movieItem.id)
        }
    }

    private fun deleteFromFavorites(id: Int){
        viewModelScope.launch {
            deleteMovieItemUseCase.invoke(id)
        }
    }


    private fun startLoading(){
        _isLoading.value = true
    }

    private fun stopLoading(){
        _isLoading.value = false
    }

    fun addMovieItem(movieItem: MovieItem){
        viewModelScope.launch {
            addMovieItemUseCase.invoke(movieItem)
        }
    }
}