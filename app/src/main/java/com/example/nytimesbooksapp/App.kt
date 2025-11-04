package com.example.nytimesbooksapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.jakewharton.threetenabp.AndroidThreeTen


@HiltAndroidApp
class MYApp: Application(){

        override fun onCreate() {
            super.onCreate()
            AndroidThreeTen.init(this)
        }
}