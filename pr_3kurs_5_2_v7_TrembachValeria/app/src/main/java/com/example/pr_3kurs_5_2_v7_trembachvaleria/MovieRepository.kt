package com.example.pr_3kurs_5_2_v7_trembachvaleria

import android.content.Context
import androidx.room.Room.databaseBuilder

class MovieRepository(context: Context) {

    private val database = databaseBuilder(
        context.applicationContext,
        MovieDatabase::class.java,
        "movies.db"
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    private val dao = database.movieDao()

    fun getAll(login: String): List<Movie> = dao.getAll(login)
    fun insert(movie: Movie) = dao.insert(movie)
    fun deleteById(id: Int) = dao.deleteById(id)
}