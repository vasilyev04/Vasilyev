package com.vasilyev.movielistapp.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_items")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String = "",
    val year: Int,
    val genres: List<String>,
    val countries: List<String> = mutableListOf(),
    val previewPosterUrl: String,
    val posterUrl: String
)