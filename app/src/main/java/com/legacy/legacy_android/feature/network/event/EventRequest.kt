package com.legacy.legacy_android.feature.network.event

data class EventRequest (
    val title: String,
    val shortDescription: String,
    val description: String,
    val startAt: String,
    val endAt: String,
    val eventImg: String,
    val links: List<Links>
)

data class Links(
    val name: String,
    val link: String
)