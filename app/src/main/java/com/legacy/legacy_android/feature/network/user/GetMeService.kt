package com.legacy.legacy_android.feature.network.user

import com.legacy.legacy_android.feature.data.core.BaseResponse
import com.legacy.legacy_android.feature.network.ruins.id.Cards
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface GetMeService {
    @GET("/user/me")
    suspend fun getMe(): BaseResponse<UserData>

    @GET("/inventory")
    suspend fun getInventory(): BaseResponse<List<InventoryItem>>

    @POST("/inventory/cardpack")
    suspend fun cardOpen(@Body request: CardOpenRequest): BaseResponse<List<Cards>>
    @GET("/user/titles")
    suspend fun getTitles(): BaseResponse<List<Title>>
    @PATCH("/user/title")
    suspend fun patchTitle(@Body styleId: TitleRequest): BaseResponse<String>
    @PATCH("/user/image")
    suspend fun patchImage(@Body profileImageUrl: ImageRequest): BaseResponse<UserData>
    @PATCH("/user/description")
    suspend fun patchDescription(@Body description: DescriptionRequest): BaseResponse<String>
}
