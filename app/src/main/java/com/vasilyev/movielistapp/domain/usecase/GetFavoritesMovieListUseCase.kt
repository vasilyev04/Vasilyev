package com.vasilyev.movielistapp.domain.usecase

import androidx.lifecycle.LiveData
import com.vasilyev.movielistapp.domain.model.MovieItem
import com.vasilyev.movielistapp.domain.repository.OfflineMovieRepository

class GetFavoritesMovieListUseCase(private val repository: OfflineMovieRepository) {

    fun invoke(): LiveData<List<MovieItem>>{
        return repository.getTopMovies()
    }

}