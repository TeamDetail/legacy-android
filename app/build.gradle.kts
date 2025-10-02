import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.dagger.hilt.android") version "2.52"
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    val MAPS_API_KEY = localProperties.getProperty("MAPS_API_KEY") ?: ""
    val KAKAO_API_KEY = localProperties.getProperty("KAKAO_API_KEY") ?: ""
    val SERVER_API_KEY = localProperties.getProperty("SERVER_API_KEY") ?: ""

    namespace = "com.legacy.legacy_android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.legacy.legacy_android"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // BuildConfigField
        buildConfigField("String", "MAPS_API_KEY", "\"$MAPS_API_KEY\"")
        buildConfigField("String", "KAKAO_API_KEY", "\"$KAKAO_API_KEY\"")
        buildConfigField("String", "SERVER_API_KEY", "\"$SERVER_API_KEY\"")

        // Manifest Placeholders
        manifestPlaceholders["MAPS_API_KEY"] = MAPS_API_KEY
        manifestPlaceholders["KAKAO_API_KEY"] = KAKAO_API_KEY
        manifestPlaceholders["SERVER_API_KEY"] = SERVER_API_KEY
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

dependencies {
    val room_version = "2.7.1"
    val nav_version = "2.8.9"

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

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

    // Kakao SDK
    implementation("com.kakao.sdk:v2-all:2.20.1")
    implementation("com.kakao.sdk:v2-user:2.20.1")
    implementation("com.kakao.sdk:v2-share:2.20.1")
    implementation("com.kakao.sdk:v2-talk:2.20.1")
    implementation("com.kakao.sdk:v2-friend:2.20.1")
    implementation("com.kakao.sdk:v2-navi:2.20.1")
    implementation("com.kakao.sdk:v2-cert:2.20.1")

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

    // Glide / Coil
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Coroutines + Play Services
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Maps
    implementation("com.google.maps.android:maps-compose:4.2.0")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Security
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("com.google.accompanist:accompanist-pager:0.36.0")

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
