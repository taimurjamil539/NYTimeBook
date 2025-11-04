package com.example.nytimesbooksapp.data.network

import com.example.nytimesbooksapp.data.model.Nytimes
import retrofit2.http.GET

interface ApiService {
    //https://api.nytimes.com/svc/books/v3/lists/overview.json?api-key=PsQi85jHGy41rIroS3XbQRA48ksAtiz7
    @GET("lists/overview.json")
    suspend fun getbooklist(): Nytimes
}