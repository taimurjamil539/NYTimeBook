package com.example.nytimesbooksapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Bookentity::class], version = 3,exportSchema = false)
abstract class Bookdatabase:RoomDatabase(){
    abstract fun bookdao(): BookDao
}

