package com.vasilyev.movielistapp.domain.usecase

import com.vasilyev.movielistapp.domain.repository.OfflineMovieRepository

class DeleteMovieItemUseCase(private val repository: OfflineMovieRepository) {

    suspend fun invoke(id: Int){
        repository.deleteMovie(id)
    }
}