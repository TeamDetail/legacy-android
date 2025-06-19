package com.legacy.legacy_android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(
            this,
            BuildConfig.KAKAO_API_KEY
        )
    }
    companion object{
        @SuppressLint("StaticFieldLack", "StaticFieldLeak")
        private lateinit var context: Context

        fun getContext() = context
    }
}