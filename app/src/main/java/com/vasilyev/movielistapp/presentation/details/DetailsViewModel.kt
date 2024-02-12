package com.vasilyev.movielistapp.presentation.details

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vasilyev.movielistapp.data.repository.MovieRepositoryImpl
import com.vasilyev.movielistapp.data.repository.OfflineMovieRepositoryImpl
import com.vasilyev.movielistapp.domain.model.MovieItem
import com.vasilyev.movielistapp.domain.usecase.GetMovieItemUseCase
import com.vasilyev.movielistapp.domain.usecase.GetOfflineMovieItemUseCase
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : ViewModel() {
    private val repository = MovieRepositoryImpl(application)
    private val offlineRepository = OfflineMovieRepositoryImpl(application)

    private val getMovieItemUseCase = GetMovieItemUseCase(repository)
    private val getOfflineMovieItemUseCase = GetOfflineMovieItemUseCase(offlineRepository)

    private val _movieItem: MutableLiveData<MovieItem> = MutableLiveData()
    val movieItem: LiveData<MovieItem>
        get() = _movieItem

    fun getMovieItemOnline(id: Int){
        viewModelScope.launch {
            _movieItem.value = getMovieItemUseCase.invoke(id)
        }
    }

    fun getMovieItemOffline(id: Int){
        viewModelScope.launch {
            _movieItem.value = getOfflineMovieItemUseCase.invoke(id)
        }
    }
}