package com.vasilyev.movielistapp.domain.model

data class MovieItem(
    val id: Int,
    val title: String,
    val description: String = "",
    val year: Int,
    val genres: List<String>,
    val countries: List<String> = mutableListOf(),
    val previewPosterUrl: String,
    val posterUrl: String,
    var isFavorite: Boolean
){
    companion object{
        const val UNDEFINED_ID = -1
    }
}