package com.vasilyev.movielistapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vasilyev.movielistapp.data.datasource.local.entity.MovieEntity
import com.vasilyev.movielistapp.data.datasource.local.mapper.MovieListMapper
import com.vasilyev.movielistapp.data.datasource.local.room.LocalDatabase
import com.vasilyev.movielistapp.domain.model.MovieItem
import com.vasilyev.movielistapp.domain.repository.OfflineMovieRepository

class OfflineMovieRepositoryImpl(application: Application): OfflineMovieRepository {
    private val roomDatabase: LocalDatabase = LocalDatabase.getInstance(application)
    private val movieListDao = roomDatabase.movieListDao()
    private val mapper: MovieListMapper = MovieListMapper()

    override fun getTopMovies(): LiveData<List<MovieItem>> {
        val list = movieListDao.getMovieList()

        return list.map { mapper.mapEntityListToModel(it) }
    }

    override suspend fun getTopMovieById(id: Int): MovieItem {
        val entity = movieListDao.getMovieEntity(id)
        return mapper.mapEntityToModel(entity)
    }

    override suspend fun addMovie(item: MovieItem) {
        val entity = mapper.mapModelToEntity(item)
        movieListDao.addMovieEntity(entity)
    }

    override suspend fun deleteMovie(id: Int) {
        movieListDao.deleteMovieEntity(id)
    }

    override suspend fun checkIsFavoriteMovie(id: Int): Boolean {
        return movieListDao.getMovieByIdFavorite(id) != null
    }
}