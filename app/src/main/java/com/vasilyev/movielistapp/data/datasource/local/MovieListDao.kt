package com.vasilyev.movielistapp.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vasilyev.movielistapp.data.datasource.local.entity.MovieEntity

@Dao
interface MovieListDao {
    @Query("SELECT * FROM movie_items")
    fun getMovieList(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movie_items WHERE id=:id")
    suspend fun getMovieEntity(id: Int): MovieEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieEntity(movieEntity: MovieEntity)

    @Query("DELETE FROM movie_items WHERE id=:id")
    suspend fun deleteMovieEntity(id: Int)

    @Query("SELECT * FROM movie_items where id=:id")
    suspend fun getMovieByIdFavorite(id: Int): MovieEntity?
}