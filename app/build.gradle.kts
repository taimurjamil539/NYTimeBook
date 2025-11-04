import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias (libs.plugins.hilt)
    kotlin("kapt")



}

val properties = Properties()
val localProperties = rootProject.file("local.properties")
properties.load(localProperties.inputStream())

android {
    namespace = "com.example.NYtimeprojectBooks"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.NYtimeprojectBooks"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        val apikey = properties.getProperty("API_KEY")
        val baseurl=properties.getProperty("BASE_URL")
        debug {
            buildConfigField("String", "BASE_URL", "\"$baseurl\"")
            buildConfigField("String", "API_KEY", "\"$apikey\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"$baseurl\"")
            buildConfigField("String", "API_KEY", "\"$apikey\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += setOf(
                "META-INF/gradle/incremental.annotation.processors",
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt"
            )
        }
    }
}

configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib:2.0.21")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:2.0.21")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:2.0.21")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.hilt)
    implementation(libs.hilt)
    implementation(libs.hilt.android.compiler)
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")


    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.0")
    // Retrofit & OkHttp - IMPORTANT: Use 4.x versions compatible with Kotlin 2.0.x
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Coil (for image loading in Compose)
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // DataStore (preferences)
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.7.0")

    // Compose UI & Material 3
    implementation("androidx.compose.ui:ui:1.6.0-alpha03")
    implementation("androidx.compose.material3:material3:1.3.0-alpha02")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0-alpha03")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0-alpha03")
    // Splashscreen
    implementation("androidx.core:core-splashscreen:1.0.0")

    implementation("androidx.room:room-runtime:2.6.1")

    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.compose.material:material:1.4.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.36.0")
    implementation("androidx.compose.material3:material3:1.3.0-alpha02")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.airbnb.android:lottie-compose:6.4.0")
    //Mock
    testImplementation("io.mockk:mockk:1.13.13")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation(kotlin("test"))
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("junit:junit:4.13.2")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.35.1-alpha")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("com.jakewharton.threetenabp:threetenabp:1.4.6")







}
kapt {
    correctErrorTypes = true
}