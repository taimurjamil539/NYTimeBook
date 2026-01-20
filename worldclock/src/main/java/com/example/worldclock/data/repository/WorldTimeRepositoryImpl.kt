package com.example.worldclockapp.data.repository
import com.example.worldclockapp.data.api.WorldTimeApi
import com.example.worldclockapp.data.mapper.toTimeZoneModel
import com.example.worldclockapp.domain.model.TimeZoneModel
import com.example.worldclockapp.domain.repository.WorldTimeRepository
class WorldTimeRepositoryImpl(
    private val api: WorldTimeApi
) : WorldTimeRepository {
    override suspend fun getTimeZones(): List<TimeZoneModel> =
        api.getTimezones().sorted().map { it.toTimeZoneModel() }
}