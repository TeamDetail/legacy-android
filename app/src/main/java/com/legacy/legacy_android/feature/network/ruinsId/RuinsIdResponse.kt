package com.legacy.legacy_android.feature.network.ruinsId

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
)