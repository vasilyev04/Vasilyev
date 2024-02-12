package com.vasilyev.movielistapp.domain.usecase

import com.vasilyev.movielistapp.domain.model.MovieItem
import com.vasilyev.movielistapp.domain.repository.MovieRepository

class GetMovieListUseCase (
    private val repository: MovieRepository
){
    suspend fun invoke(): List<MovieItem>{
        return repository.getTopMovies()
    }
}