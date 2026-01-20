package com.example.worldclockapp.data.dto
import com.squareup.moshi.Json
data class WorldTimeDto(
    val abbreviation: String?,
    val datetime: String?,
    @Json(name = "utc_offset") val utcOffset: String?,
    val timezone: String?
)