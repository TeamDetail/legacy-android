package com.legacy.legacy_android.di

import com.legacy.legacy_android.BuildConfig
import com.legacy.legacy_android.feature.network.login.LoginService
import com.legacy.legacy_android.feature.network.ruins.RuinsMapService
import com.legacy.legacy_android.feature.network.user.GetMeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_API_KEY)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideRuinsMapService(retrofit: Retrofit): RuinsMapService {
        return retrofit.create(RuinsMapService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetMeService(retrofit: Retrofit): GetMeService {
        return retrofit.create(GetMeService::class.java)
    }
}