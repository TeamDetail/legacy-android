package com.legacy.legacy_android.feature.network.fcm

data class FcmRequest (
    val lat: Double,
    val lng: Double,
    val title: String,
    val targetToken: String
)