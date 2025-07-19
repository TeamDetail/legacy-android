package com.legacy.legacy_android.feature.network.core.token

import android.content.Context
import com.legacy.legacy_android.BuildConfig
import com.legacy.legacy_android.feature.network.token.TokenService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private var appContext: Context? = null
    private const val BASE_URL = BuildConfig.SERVER_API_KEY

    fun init(context: Context) {
        this.appContext = context.applicationContext
    }

    private fun requireContext(): Context {
        return appContext ?: throw IllegalStateException("RetrofitInstance not initialized")
    }

    private val tokenRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val tokenService: TokenService by lazy {
        tokenRetrofit.create(TokenService::class.java)
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(TokenInterceptor(requireContext(), tokenService))
                    .build()
            )
            .build()
    }

    val apiService: TokenService by lazy {
        retrofit.create(TokenService::class.java)
    }
}