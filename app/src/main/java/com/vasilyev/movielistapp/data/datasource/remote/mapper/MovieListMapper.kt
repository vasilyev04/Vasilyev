package com.vasilyev.movielistapp.data.datasource.remote.mapper

import com.vasilyev.movielistapp.data.datasource.remote.entity.DetailedMovieEntity
import com.vasilyev.movielistapp.data.datasource.remote.entity.MovieEntity
import com.vasilyev.movielistapp.domain.model.MovieItem

class MovieListMapper {

    fun mapEntityToModel(movieEntity: MovieEntity): MovieItem{
        val genres = mutableListOf<String>()
        movieEntity.genres.forEach { genres.add(it.genre) }

        return MovieItem(
            id = movieEntity.filmId,
            title = movieEntity.nameRu,
            year = movieEntity.year,
            genres = genres,
            previewPosterUrl = movieEntity.posterUrlPreview,
            posterUrl = movieEntity.posterUrl,
            isFavorite = false
        )
    }

    fun mapDetailedEntityToModel(movieEntity: DetailedMovieEntity): MovieItem{
        val genres = mutableListOf<String>()
        movieEntity.genres.forEach { genres.add(it.genre) }

        val countries = mutableListOf<String>()
        movieEntity.countries.forEach { countries.add(it.country) }

        return MovieItem(
            id = movieEntity.kinopoiskId,
            title = movieEntity.nameRu,
            description = movieEntity.description,
            previewPosterUrl = movieEntity.posterUrlPreview,
            posterUrl = movieEntity.posterUrl,
            genres = genres,
            countries = countries,
            year = movieEntity.year,
            isFavorite = false
        )
    }

    fun mapEntityListToModel(movieList: List<MovieEntity>): List<MovieItem>{
        return movieList.map { mapEntityToModel(it) }
    }
}