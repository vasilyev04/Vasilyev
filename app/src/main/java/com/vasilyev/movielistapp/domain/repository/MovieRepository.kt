package com.vasilyev.movielistapp.domain.repository

import com.vasilyev.movielistapp.domain.model.MovieItem

interface MovieRepository {
    suspend fun getTopMovies(): List<MovieItem>

    suspend fun getTopMovieById(id: Int): MovieItem
}