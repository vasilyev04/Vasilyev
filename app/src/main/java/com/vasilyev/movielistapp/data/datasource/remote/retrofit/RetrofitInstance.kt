package com.vasilyev.movielistapp.data.datasource.remote.retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"

    private val _retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: MovieApi by lazy{
        _retrofit.create(MovieApi::class.java)
    }
}