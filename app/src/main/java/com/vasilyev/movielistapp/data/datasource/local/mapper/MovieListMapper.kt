package com.vasilyev.movielistapp.data.datasource.local.mapper

import com.vasilyev.movielistapp.data.datasource.local.entity.MovieEntity
import com.vasilyev.movielistapp.data.datasource.remote.entity.DetailedMovieEntity
import com.vasilyev.movielistapp.domain.model.MovieItem

class MovieListMapper {

    fun mapEntityToModel(movieEntity: MovieEntity): MovieItem{
        return MovieItem(
            id = movieEntity.id,
            title = movieEntity.title,
            year = movieEntity.year,
            genres = movieEntity.genres,
            description = movieEntity.description,
            countries = movieEntity.countries,
            previewPosterUrl = movieEntity.previewPosterUrl,
            posterUrl = movieEntity.posterUrl,
            isFavorite = true
        )
    }

    fun mapModelToEntity(movieItem: MovieItem): MovieEntity{
        return MovieEntity(
            id = movieItem.id,
            title = movieItem.title,
            year = movieItem.year,
            genres = movieItem.genres,
            previewPosterUrl = movieItem.previewPosterUrl,
            posterUrl = movieItem.posterUrl,
            description = movieItem.description,
            countries = movieItem.countries
        )
    }

    fun mapEntityListToModel(movieList: List<MovieEntity>): List<MovieItem>{
        return movieList.map { mapEntityToModel(it) }
    }
}