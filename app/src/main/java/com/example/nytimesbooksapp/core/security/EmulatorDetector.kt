package com.example.nytimesbooksapp.core.security
import android.os.Build

object EmulatorDetector {
    fun isEmulator(): Boolean {
        val model = Build.MODEL.lowercase()
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.contains("unknown")
                || Build.MODEL.contains("google_sdk")
                || model.contains("emulator")
                || model.contains("android sdk built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.contains("generic")
                || Build.PRODUCT.contains("sdk_google")
                || Build.DEVICE.contains("generic"))
    }
}
