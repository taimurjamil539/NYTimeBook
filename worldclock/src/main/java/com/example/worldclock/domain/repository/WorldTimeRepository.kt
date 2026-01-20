package com.example.worldclockapp.domain.repository
import com.example.worldclockapp.domain.model.TimeZoneModel
interface WorldTimeRepository {
    suspend fun getTimeZones(): List<TimeZoneModel>
}