package com.legacy.legacy_android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.legacy.legacy_android.feature.network.core.remote.LegacyRetrofitClient
import com.legacy.legacy_android.feature.network.core.remote.RetrofitClient

class LegacyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RetrofitClient.init(this)
        LegacyRetrofitClient.init()
        context = applicationContext
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context

        fun getContext() = context
    }
}