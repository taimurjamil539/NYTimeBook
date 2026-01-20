package com.example.worldclockapp.domain.usecase
import com.example.worldclockapp.domain.model.TimeZoneModel
import com.example.worldclockapp.domain.repository.WorldTimeRepository
class GetTimeZonesUseCase(private val repo: WorldTimeRepository) {
    suspend operator fun invoke(): List<TimeZoneModel> = repo.getTimeZones()
}