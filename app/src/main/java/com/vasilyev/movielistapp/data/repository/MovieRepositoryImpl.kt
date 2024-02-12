package com.vasilyev.movielistapp.data.repository

import android.app.Application
import android.util.Log
import com.vasilyev.movielistapp.data.datasource.remote.retrofit.RetrofitInstance
import com.vasilyev.movielistapp.data.datasource.remote.mapper.MovieListMapper
import com.vasilyev.movielistapp.domain.model.MovieItem
import com.vasilyev.movielistapp.domain.repository.MovieRepository
import java.lang.RuntimeException

class MovieRepositoryImpl(application: Application) : MovieRepository{
    private val mapper = MovieListMapper()
    private val offlineRepository = OfflineMovieRepositoryImpl(application)

    override suspend fun getTopMovies(): List<MovieItem>{
        val response = RetrofitInstance.api.getTopMovies()

        /*if(!response.isSuccessful){
            throw NoConnectivityException()
        }*/

        val movieListEntity = response.body()?.films
            ?: throw RuntimeException("Move items list is not received")

        val list = mapper.mapEntityListToModel(movieListEntity)
        for (movieItem in list){
            movieItem.isFavorite = offlineRepository.checkIsFavoriteMovie(movieItem.id)
        }

        return list
    }

    override suspend fun getTopMovieById(id: Int): MovieItem {
        val movieEntity = RetrofitInstance.api.getMovie(id).body()
            ?: throw RuntimeException("Movie item with ID $id is not received")

        return mapper.mapDetailedEntityToModel(movieEntity)
    }
}