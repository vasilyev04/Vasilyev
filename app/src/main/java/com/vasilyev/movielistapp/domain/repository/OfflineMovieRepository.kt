package com.vasilyev.movielistapp.domain.repository

import androidx.lifecycle.LiveData
import com.vasilyev.movielistapp.domain.model.MovieItem

interface OfflineMovieRepository {
    fun getTopMovies(): LiveData<List<MovieItem>>

    suspend fun getTopMovieById(id: Int): MovieItem

    suspend fun addMovie(item: MovieItem)

    suspend fun deleteMovie(id: Int)

    suspend fun checkIsFavoriteMovie(id: Int): Boolean
}