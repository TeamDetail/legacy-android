package com.legacy.legacy_android.feature.network.user

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET

interface GetMeService {
    @GET("/user/me")
    suspend fun profile(): BaseResponse<GetMeResponse>
}