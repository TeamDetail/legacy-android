import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

// local.properties 로드
val localProps = rootProject.file("local.properties")
val props = Properties().apply { load(localProps.inputStream()) }

// 👇 keystore.properties 로드
val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "com.legacy.legacy_android"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.legacy.legacy_android"
        minSdk = 24
        targetSdk = 35
        versionCode = 16
        versionName = "1.0.6"
        multiDexEnabled = true

        buildConfigField("String", "MAPS_API_KEY", "\"${props.getProperty("MAPS_API_KEY", "")}\"")
        buildConfigField("String", "KAKAO_API_KEY", "\"${props.getProperty("KAKAO_API_KEY", "")}\"")
        buildConfigField("String", "GOOGLE_WEBCLIENT_KEY", "\"${props.getProperty("ANDROID_WEBCLIENT_KEY", "")}\"")
        buildConfigField("String", "SERVER_API_KEY", "\"${props.getProperty("SERVER_API_KEY", "")}\"")

        manifestPlaceholders["MAPS_API_KEY"] = props.getProperty("MAPS_API_KEY", "")
        manifestPlaceholders["KAKAO_API_KEY"] = props.getProperty("KAKAO_API_KEY", "")
        manifestPlaceholders["GOOGLE_WEBCLIENT_KEY"] = props.getProperty("ANDROID_WEBCLIENT_KEY", "")
        manifestPlaceholders["SERVER_API_KEY"] = props.getProperty("SERVER_API_KEY", "")
    }

    // 👇 서명 설정
    signingConfigs {
        create("release") {
            if (keystorePropertiesFile.exists()) {
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
            }
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    lint {
        abortOnError = false
        checkReleaseBuilds = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/NOTICE",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE.txt"
            )
        }
    }
}

dependencies {
    // ... 기존 dependencies 그대로 ...
}

dependencies {
    val room_version = "2.7.1"
    val nav_version = "2.8.9"
    val composeBom = platform("androidx.compose:compose-bom:2024.12.01")

    implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")

    // Google Credential Manager
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.1")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Room
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    kapt("androidx.room:room-compiler:$room_version")

    // Kakao SDK
    implementation("com.kakao.sdk:v2-all:2.20.1")

    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.datastore:datastore:1.0.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    // Compose - 👇 BOM을 먼저 선언하고 모든 Compose 라이브러리에 적용
    implementation(composeBom)
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.foundation:foundation")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Accompanist
    implementation("com.google.accompanist:accompanist-permissions:0.36.0")
    implementation("com.google.accompanist:accompanist-pager:0.36.0")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.36.0")

    // Capturable
    implementation("dev.shreyaspatil:capturable:2.1.0")

    // Navigation
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Google OAuth
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Google Maps - 👇 BOM 강제 적용
    implementation("com.google.maps.android:maps-compose:4.2.0") {
        exclude(group = "androidx.compose", module = "compose-bom")
    }
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    // Image Loading
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}