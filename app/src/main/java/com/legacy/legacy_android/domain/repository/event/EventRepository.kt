package com.legacy.legacy_android.domain.repository.event

import com.legacy.legacy_android.feature.network.event.EventResponse
import com.legacy.legacy_android.feature.network.event.EventService
import com.legacy.legacy_android.feature.network.friend.FriendResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @Inject constructor(
    private val eventService: EventService,
) {
    suspend fun getAllEvents(): Result<List<EventResponse>> {
        return try {
            val response = eventService.getEvents()
            val data = response.data
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}