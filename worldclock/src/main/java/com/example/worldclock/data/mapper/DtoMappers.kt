package com.example.worldclockapp.data.mapper
import com.example.worldclockapp.domain.model.TimeZoneModel
fun String.toTimeZoneModel(): TimeZoneModel =
    TimeZoneModel(
        id = this,
        displayName = this.substringAfterLast('/').replace('_', ' ')
    )