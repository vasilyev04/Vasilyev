package com.vasilyev.movielistapp.domain.usecase

import com.vasilyev.movielistapp.domain.model.MovieItem
import com.vasilyev.movielistapp.domain.repository.OfflineMovieRepository

class GetOfflineMovieItemUseCase(private val repository: OfflineMovieRepository) {

    suspend fun invoke(id: Int): MovieItem {
        return repository.getTopMovieById(id)
    }
}