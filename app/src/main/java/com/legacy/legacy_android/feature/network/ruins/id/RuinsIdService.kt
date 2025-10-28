package com.legacy.legacy_android.feature.network.ruins.id

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RuinsIdService {
    @GET("/ruins/{id}")
    suspend fun getRuinsById(
        @Path("id") id: Int
    ): BaseResponse<RuinsIdResponse>

    @GET("/ruins/comment/{id}")
    suspend fun getCommentById(
        @Path("id") id: Int
    ): BaseResponse<List<RuinsCommentResponse>>

    @POST("/ruins/comment")
    suspend fun postComment(
        @Body comment: RuinsCommentRequest
    ): BaseResponse<RuinsCommentResponse>

}