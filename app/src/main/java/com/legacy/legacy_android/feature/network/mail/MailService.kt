package com.legacy.legacy_android.feature.network.mail

import retrofit2.http.GET
import retrofit2.http.POST

interface MailService {
    @GET("/mail")
    suspend fun getMails(): List<MailResponse>

    @POST("/mail/allGet")
    suspend fun getItems(): List<MailResponse>
}