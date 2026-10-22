package com.example.pr_3kurs_5_2_v7_trembachvaleria

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie")
    fun getAll(): List<Movie>

    @Insert
    fun insert(movie: Movie)
}