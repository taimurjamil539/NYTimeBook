package com.example.nytimesbooksapp.core.security

import java.io.File

object RootDetector {

    private val rootPaths = listOf(
        "/system/app/Superuser.apk",
        "/sbin/su",
        "/system/bin/su",
        "/system/xbin/su",
        "/data/local/xbin/su",
        "/data/local/bin/su",
        "/system/sd/xbin/su"
    )

    fun isDeviceRooted(): Boolean {
        return rootPaths.any { File(it).exists() } || canExecuteSuCommand()
    }

    private fun canExecuteSuCommand(): Boolean {
        return try {
            Runtime.getRuntime().exec("su")
            true
        } catch (e: Exception) {
            false
        }
    }
}
