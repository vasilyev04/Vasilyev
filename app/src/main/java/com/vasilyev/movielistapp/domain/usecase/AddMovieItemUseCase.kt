package com.vasilyev.movielistapp.domain.usecase

import com.vasilyev.movielistapp.domain.model.MovieItem
import com.vasilyev.movielistapp.domain.repository.OfflineMovieRepository

class AddMovieItemUseCase(private val repository: OfflineMovieRepository) {

    suspend fun invoke(movieItem: MovieItem){
        repository.addMovie(movieItem)
    }

}