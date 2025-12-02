package com.legacy.legacy_android.feature.screen.event.model

import com.legacy.legacy_android.feature.network.event.EventRequest
import com.legacy.legacy_android.feature.network.event.EventResponse


data class EventUiState(
    val eventList: List<EventResponse> = emptyList(),
    val currentEvent: EventRequest? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
