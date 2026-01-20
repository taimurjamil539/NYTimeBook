package com.example.worldclockapp.data.api


import com.example.worldclockapp.data.dto.WorldTimeDto
import retrofit2.http.GET
import retrofit2.http.Path


interface WorldTimeApi {
    @GET("/api/timezone")
    suspend fun getTimezones(): List<String>
    @GET("/api/timezone/{zone}")
    suspend fun getZone(@Path("zone") zone: String): WorldTimeDto
}