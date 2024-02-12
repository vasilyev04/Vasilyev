package com.vasilyev.movielistapp.data.datasource.remote.entity

data class DetailedMovieEntity(
    val countries: List<Country>,
    val description: String,
    val genres: List<Genre>,
    val kinopoiskId: Int,
    val nameEn: String,
    val nameOriginal: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val year: Int
)
