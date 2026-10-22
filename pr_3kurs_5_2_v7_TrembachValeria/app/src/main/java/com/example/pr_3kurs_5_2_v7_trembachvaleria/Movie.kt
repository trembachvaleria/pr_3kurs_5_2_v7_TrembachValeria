package com.example.pr_3kurs_5_2_v7_trembachvaleria

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val year: String,
    val director: String,
    val actors: String
)