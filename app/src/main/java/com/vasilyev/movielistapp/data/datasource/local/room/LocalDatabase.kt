package com.vasilyev.movielistapp.data.datasource.local.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vasilyev.movielistapp.data.datasource.local.MovieListDao
import com.vasilyev.movielistapp.data.datasource.local.entity.MovieEntity
import com.vasilyev.movielistapp.data.datasource.local.entity.StringListConverter
import androidx.room.TypeConverters


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun movieListDao(): MovieListDao

    companion object {
        private var INSTANCE: LocalDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "movie_list.db"

        fun getInstance(application: Application): LocalDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }

                val db = Room.databaseBuilder(
                    application,
                    LocalDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = db
                return db
            }
        }
    }
}
