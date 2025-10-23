import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.legacy.legacy_android"
    compileSdk = 35

    // app/gradle.properties 또는 project gradle.properties에서 읽기
    val mapsApiKey = project.findProperty("MAPS_API_KEY") as String? ?: ""
    val kakaoApiKey = project.findProperty("KAKAO_API_KEY") as String? ?: ""
    val serverApiKey = project.findProperty("SERVER_API_KEY") as String? ?: ""
    val androidWebclientKey = project.findProperty("ANDROID_WEBCLIENT_KEY") as String? ?: ""

    defaultConfig {
        applicationId = "com.legacy.legacy_android"
        minSdk = 28
        targetSdk = 35
        versionCode = 3
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // BuildConfigField
        buildConfigField("String", "MAPS_API_KEY", "\"$mapsApiKey\"")
        buildConfigField("String", "KAKAO_API_KEY", "\"$kakaoApiKey\"")
        buildConfigField("String", "SERVER_API_KEY", "\"$serverApiKey\"")
        buildConfigField("String", "ANDROID_WEBCLIENT_KEY", "\"$androidWebclientKey\"")

        // Manifest placeholders
        manifestPlaceholders += mapOf(
            "MAPS_API_KEY" to mapsApiKey,
            "KAKAO_API_KEY" to kakaoApiKey,
            "SERVER_API_KEY" to serverApiKey,
            "ANDROID_WEBCLIENT_KEY" to androidWebclientKey
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn"
        )
        freeCompilerArgs += listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${project.buildDir.absolutePath}/compose_metrics",
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:metricsDestination=${project.buildDir.absolutePath}/compose_metrics"
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

// dependencies는 그대로 유지
dependencies {
    implementation(libs.androidx.compose.ui.util)
    implementation(libs.androidx.compose.foundation.layout)
    val room_version = "2.7.1"
    val nav_version = "2.8.9"

    // Kotlin Immutable Collections
    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-process:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    // Room
    implementation("androidx.room:room-runtime:$room_version")

    // Kakao SDK (v2-all만 사용)
    implementation("com.kakao.sdk:v2-all:2.20.1")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore:1.0.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Accompanist
    implementation("com.google.accompanist:accompanist-swiperefresh:0.36.0")
    implementation("com.google.accompanist:accompanist-navigation-animation:0.30.0")
    implementation("com.google.accompanist:accompanist-permissions:0.28.0")
    implementation("com.google.accompanist:accompanist-pager:0.36.0")

    // Glide / Coil
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("com.google.accompanist:accompanist-swiperefresh:0.36.0")


    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Coroutines + Play Services
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // Credentials & Google Identity
    implementation("com.auth0.android:auth0:2.1.0")
    implementation("androidx.credentials:credentials:1.2.2")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.material3:material3:1.3.0")

    // Maps & Location
    implementation("com.google.maps.android:maps-compose:4.2.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Security
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // Captureable
    implementation("dev.shreyaspatil:capturable:2.1.0")

    // Compose (BOM 사용)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
