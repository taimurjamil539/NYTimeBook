package com.example.nytimesbooksapp.data.common.appConfig

import com.example.NYtimeprojectBooks.BuildConfig


sealed class Appconfig {
    data object Network : Appconfig() { const val BASE_URL = BuildConfig.BASE_URL }

    data object Keys : Appconfig() { const val API_KEY = BuildConfig.API_KEY }
}


