package com.legacy.legacy_android.feature.network.event

import com.legacy.legacy_android.feature.data.core.BaseResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventService {
    @GET("/events")
    suspend fun getEvents(): BaseResponse<List<EventResponse>>

    @GET("/event/{id}")
    suspend fun getEventById(@Path("id") id: Int): BaseResponse<EventResponse>

    @POST("/events")
    suspend fun createEvent(@Body data: EventRequest): BaseResponse<String>
}
