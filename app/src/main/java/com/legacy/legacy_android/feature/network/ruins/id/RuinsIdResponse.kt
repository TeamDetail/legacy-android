package com.legacy.legacy_android.feature.network.ruins.id

import java.util.Date

data class RuinsIdResponse (
    val ruinsId: Int,
    val ruinsImage: String,
    val category: String,
    val name: String,
    val chineseName: String,
    val englishName: String,
    val location: String,
    val detailAddress: String,
    val periodName: String,
    val specifiedDate: Date,
    val owner: String,
    val manager: String,
    val latitude: Double,
    val longitude: Double,
    val card: Cards?
)


data class CourseRuinsResponse(
    val clear: Boolean,
    val data: RuinsIdResponse
)

data class Cards(
    val cardId: Int,
    val cardName: String,
    val cardImageUrl: String,
    val cardType: String?,
    val nationAttributeName: String,
    val lineAttributeName: String,
    val regionAttributeName: String
)
