package com.legacy.legacy_android.di

import com.legacy.legacy_android.feature.network.block.Get.GetBlockService
import com.legacy.legacy_android.feature.network.block.Post.PostBlockService
import com.legacy.legacy_android.feature.network.core.remote.RetrofitClient
import com.legacy.legacy_android.feature.network.login.LoginService
import com.legacy.legacy_android.feature.network.quiz.getquiz.GetQuizService
import com.legacy.legacy_android.feature.network.ruins.RuinsMapService
import com.legacy.legacy_android.feature.network.ruinsId.RuinsIdService
import com.legacy.legacy_android.feature.network.token.TokenService
import com.legacy.legacy_android.feature.network.user.GetMeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return RetrofitClient.getRetrofit()
    }

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenService(retrofit: Retrofit): TokenService {
        return retrofit.create(TokenService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetMeService(retrofit: Retrofit): GetMeService {
        return retrofit.create(GetMeService::class.java)
    }

    @Provides
    @Singleton
    fun provideRuinsMapService(retrofit: Retrofit): RuinsMapService {
        return retrofit.create(RuinsMapService::class.java)
    }

    @Provides
    @Singleton
    fun provideRuinsIdService(retrofit: Retrofit): RuinsIdService {
        return retrofit.create(RuinsIdService::class.java)
    }

    @Provides
    @Singleton
    fun providePostBlockService(retrofit: Retrofit): PostBlockService{
        return retrofit.create(PostBlockService::class.java)
    }

    @Provides
    @Singleton
    fun provideGetBlockService(retrofit: Retrofit): GetBlockService{
        return retrofit.create(GetBlockService::class.java)
    }
    @Provides
    @Singleton
    fun provideGetQuizService(retrofit: Retrofit): GetQuizService{
        return retrofit.create(GetQuizService::class.java)
    }
}
