package com.example.nytimesbooksapp.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class Bookentity(
    @PrimaryKey

    val title: String,
    val auther: String,
    val description: String,
    val rank: Int,
    val image: String,
    val publisher: String,
    val buylink:String,
    val createDate:String

)