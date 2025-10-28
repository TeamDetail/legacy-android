package com.legacy.legacy_android.feature.network.mail

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.GET
import retrofit2.http.POST

interface MailService {
    @GET("/mail")
    suspend fun getMails(): BaseResponse<List<MailResponse>>

    @POST("/mail/allGet")
    suspend fun getItems(): BaseResponse<List<MailResponse>>
}