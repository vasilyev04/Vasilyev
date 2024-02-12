package com.vasilyev.movielistapp.data.datasource.remote.entity

data class MovieEntity(
    val filmId: Int,
    val genres: List<Genre>,
    val description: String?,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val year: Int
)