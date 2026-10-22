package com.example.pr_3kurs_5_2_v7_trembachvaleria

import android.content.Context
import androidx.room.Room
import androidx.room.Room.databaseBuilder

class MovieRepository(context: Context) {

    private val database = databaseBuilder(
        context.applicationContext,
        MovieDatabase::class.java,
        "movies.db"
    )
        .allowMainThreadQueries()
        .build()

    private val dao = database.movieDao()

    fun getAll(): List<Movie> = dao.getAll()
    fun insert(movie: Movie) = dao.insert(movie)
}