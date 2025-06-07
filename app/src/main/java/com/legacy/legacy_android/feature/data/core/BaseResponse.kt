package com.legacy.legacy_android.feature.data.core

data class BaseResponse<T>(
    val data: T? = null,
    val status:Int
)