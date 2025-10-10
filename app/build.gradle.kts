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
    namespace = "com.legacy.legacy_android"
    compileSdk = 35

    // local.properties 로드
    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(localPropertiesFile.inputStream())
    }

    val MAPS_API_KEY = localProperties.getProperty("MAPS_API_KEY") ?: ""
    val KAKAO_API_KEY = localProperties.getProperty("KAKAO_API_KEY") ?: ""
    val SERVER_API_KEY = localProperties.getProperty("SERVER_API_KEY") ?: ""
    val ANDROID_WEBCLIENT_KEY = localProperties.getProperty("ANDROID_WEBCLIENT_KEY") ?: ""
    val auth0Domain = localProperties.getProperty("AUTH0_DOMAIN") ?: ""
    val auth0Scheme = localProperties.getProperty("AUTH0_SCHEME") ?: ""
    val appleClientId = localProperties.getProperty("APPLE_CLIENT_ID") ?: ""
    val appleKeyId = localProperties.getProperty("APPLE_KEY_ID") ?: ""
    val appleTeamId = localProperties.getProperty("APPLE_TEAM_ID") ?: ""
    val appleRedirectUrl = localProperties.getProperty("APPLE_REDIRECT_URL") ?: ""


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
        buildConfigField("String", "ANDROID_WEBCLIENT_KEY", "\"$ANDROID_WEBCLIENT_KEY\"")
        buildConfigField ("String", "APPLE_CLIENT_ID", "\"${localProperties.getProperty("APPLE_CLIENT_ID")}\"")
        buildConfigField ("String", "APPLE_KEY_ID", "\"${localProperties.getProperty("APPLE_KEY_ID")}\"")
        buildConfigField ("String", "APPLE_TEAM_ID", "\"${localProperties.getProperty("APPLE_TEAM_ID")}\"")
        buildConfigField ("String", "APPLE_REDIRECT_URL", "\"${localProperties.getProperty("APPLE_REDIRECT_URL")}\"")

        // Manifest placeholders
        manifestPlaceholders += mapOf(
            "MAPS_API_KEY" to MAPS_API_KEY,
            "KAKAO_API_KEY" to KAKAO_API_KEY,
            "SERVER_API_KEY" to SERVER_API_KEY,
            "ANDROID_WEBCLIENT_KEY" to ANDROID_WEBCLIENT_KEY,
            "auth0Domain" to auth0Domain,
            "auth0Scheme" to auth0Scheme,
            "appleClientId" to appleClientId,
            "appleKeyId" to appleKeyId,
            "appleTeamId" to appleTeamId,
            "appleRedirectUrl" to appleRedirectUrl
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
