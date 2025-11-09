package com.example.nytimesbooksapp.data.common.appconfig

import com.example.NYtimeprojectBooks.BuildConfig


sealed class AppConfig {
    data object Network : AppConfig() { const val BASE_URL = BuildConfig.BASE_URL }
    data object Keys : AppConfig() { const val API_KEY = BuildConfig.API_KEY }
}


