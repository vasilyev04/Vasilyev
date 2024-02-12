package com.vasilyev.movielistapp.data.datasource.remote.retrofit

import com.vasilyev.movielistapp.data.datasource.remote.entity.DetailedMovieEntity
import com.vasilyev.movielistapp.data.datasource.remote.entity.MovieEntityList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/top")
    suspend fun getTopMovies(
        @Query("type") type: String = BASE_LIST_API_PARAMETER,
    ): Response<MovieEntityList>

    @Headers("X-API-KEY: $API_KEY")
    @GET("/api/v2.2/films/{id}")
    suspend fun getMovie(@Path("id") id: Int): Response<DetailedMovieEntity>

    companion object{
        //Should be moved to environmental variables, but rested fot the test
        private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
        private const val BASE_LIST_API_PARAMETER = "TOP_100_POPULAR_FILMS"
    }
}