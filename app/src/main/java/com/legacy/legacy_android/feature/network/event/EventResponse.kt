package com.legacy.legacy_android.feature.network.event

data class EventResponse(
    val eventId: Int,
    val title: String,
    val shortDescription: String,
    val startAt: String,
    val endAt: String,
    val eventImg: String,
)
