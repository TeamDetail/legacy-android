// RetrofitClient.kt
package com.legacy.legacy_android.feature.network.core.remote

import android.content.Context
import com.google.gson.GsonBuilder
import com.legacy.legacy_android.BuildConfig
import com.legacy.legacy_android.feature.data.core.remote.ResponseInterceptor
import com.legacy.legacy_android.feature.network.login.LoginService
import com.legacy.legacy_android.feature.network.ruins.RuinsMapService
import com.legacy.legacy_android.feature.network.token.TokenService
import com.legacy.legacy_android.feature.network.user.GetMeService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private var retrofit: Retrofit? = null

    fun init(context: Context) {
        val gson = GsonBuilder().setLenient().create()

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(RequestInterceptor(NetworkUtil(context)))
            .addInterceptor(ResponseInterceptor())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_API_KEY)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun getRetrofit(): Retrofit {
        return retrofit ?: throw IllegalStateException("RetrofitClient not initialized")
    }

    val loginService: LoginService by lazy {
        getRetrofit().create(LoginService::class.java)
    }

    val tokenService: TokenService by lazy {
        getRetrofit().create(TokenService::class.java)
    }

    val getMeService: GetMeService by lazy {
        getRetrofit().create(GetMeService::class.java)
    }

    val ruinsMapService: RuinsMapService by lazy {
        getRetrofit().create(RuinsMapService::class.java)
    }
}


object LegacyRetrofitClient {
    private var retrofit: Retrofit? = null

    fun init() {
        val gson = GsonBuilder().setLenient().create()

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_API_KEY)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return retrofit ?: throw IllegalStateException("BeepRetrofitClient is not initialized")
    }

}
