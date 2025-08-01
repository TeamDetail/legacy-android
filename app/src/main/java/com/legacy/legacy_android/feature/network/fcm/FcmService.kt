package com.legacy.legacy_android.feature.network.fcm

import retrofit2.http.Body
import retrofit2.http.POST

interface FcmService {
    @POST("/alarm/location")
    suspend fun location(@Body code: FcmRequest): FcmResponse
}
