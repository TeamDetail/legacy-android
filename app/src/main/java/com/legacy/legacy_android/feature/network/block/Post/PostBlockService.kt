package com.legacy.legacy_android.feature.network.block.Post
import retrofit2.http.Body
import retrofit2.http.POST

interface PostBlockService {
    @POST("/block")
    suspend fun block( @Body request: PostBlockRequest): PostBlockResponse
}