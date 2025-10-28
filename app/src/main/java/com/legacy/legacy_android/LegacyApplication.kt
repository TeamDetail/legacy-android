package com.legacy.legacy_android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import com.legacy.legacy_android.feature.network.core.remote.LegacyRetrofitClient
import com.legacy.legacy_android.feature.network.core.remote.RetrofitClient
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LegacyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        RetrofitClient.init(this)
        LegacyRetrofitClient.init()
        RetrofitClient.init(applicationContext)
        context = applicationContext
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getContext() = context
    }
}