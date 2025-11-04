package com.example.nytimesbooksapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookDao{
    @Query("SELECT * FROM Books")
    suspend fun getallbooks(): List<Bookentity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertbook(books: List<Bookentity>)
    @Query("DELETE  FROM Books")
    suspend fun clearbooks()
    @Query("SELECT * FROM Books WHERE title LIKE  '%' || :query || '%' OR auther LIKE '%' || :query || '%'"  )
    suspend fun searchbook(query:String): List<Bookentity>


}