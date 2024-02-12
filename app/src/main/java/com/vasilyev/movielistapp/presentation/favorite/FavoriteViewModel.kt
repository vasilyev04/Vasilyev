package com.vasilyev.movielistapp.presentation.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasilyev.movielistapp.data.repository.OfflineMovieRepositoryImpl
import com.vasilyev.movielistapp.domain.model.MovieItem
import com.vasilyev.movielistapp.domain.usecase.DeleteMovieItemUseCase
import com.vasilyev.movielistapp.domain.usecase.GetFavoritesMovieListUseCase
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : ViewModel() {
    private val repository = OfflineMovieRepositoryImpl(application)

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _loadingFailed: MutableLiveData<Any> = MutableLiveData()
    val loadingFailed: LiveData<Any>
        get() = _loadingFailed

    private val getFavoritesMovieListUseCase = GetFavoritesMovieListUseCase(repository)
    private val deleteMovieItemUseCase = DeleteMovieItemUseCase(repository)

    val movieList: LiveData<List<MovieItem>> = getFavoritesMovieListUseCase.invoke()

    fun deleteMovie(id: Int){
        viewModelScope.launch {
            deleteMovieItemUseCase.invoke(id)
        }
    }
}