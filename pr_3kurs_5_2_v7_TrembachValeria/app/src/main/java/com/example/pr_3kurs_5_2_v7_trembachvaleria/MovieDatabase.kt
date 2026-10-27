package com.example.pr_3kurs_5_2_v7_trembachvaleria

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 2)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}