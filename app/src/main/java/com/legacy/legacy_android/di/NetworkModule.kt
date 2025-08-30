package com.legacy.legacy_android.di

import com.legacy.legacy_android.feature.network.block.Get.GetBlockService
import com.legacy.legacy_android.feature.network.block.Post.PostBlockService
import com.legacy.legacy_android.feature.network.core.remote.RetrofitClient
import com.legacy.legacy_android.feature.network.login.LoginService
import com.legacy.legacy_android.feature.network.quiz.getquiz.GetQuizService
import com.legacy.legacy_android.feature.network.quiz.postQuizAnswer.PostQuizAnswerService
import com.legacy.legacy_android.feature.network.ruins.RuinsMapService
import com.legacy.legacy_android.feature.network.ruins.id.RuinsIdService
import com.legacy.legacy_android.feature.network.token.TokenService
import com.legacy.legacy_android.feature.network.user.GetMeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import android.content.Context
import com.legacy.legacy_android.domain.repository.TokenRepository
import com.legacy.legacy_android.domain.repository.TokenRepositoryImpl
import com.legacy.legacy_android.domain.repository.market.MarketRepository
import com.legacy.legacy_android.feature.network.auth.KakaoLoginManager
import com.legacy.legacy_android.feature.network.auth.KakaoLoginManagerImpl
import com.legacy.legacy_android.feature.network.card.CardService
import com.legacy.legacy_android.feature.network.course.all.AllCourseService
import com.legacy.legacy_android.feature.network.course.all.EventCourseService
import com.legacy.legacy_android.feature.network.course.all.PopularCourseService
import com.legacy.legacy_android.feature.network.course.all.RecentCourseService
import com.legacy.legacy_android.feature.network.course.search.SearchCourseService
import com.legacy.legacy_android.feature.network.fcm.FcmService
import com.legacy.legacy_android.feature.network.market.MarketService
import com.legacy.legacy_android.feature.network.rank.RankingService
import com.legacy.legacy_android.feature.network.ruins.search.RuinsSearchService
import dagger.Binds
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    @Singleton
    abstract fun bindKakaoLoginManager(
        kakaoLoginManagerImpl: KakaoLoginManagerImpl
    ): KakaoLoginManager

    @Binds
    @Singleton
    abstract fun bindTokenRepository(
        tokenRepositoryImpl: TokenRepositoryImpl
    ): TokenRepository
}

@Module
@InstallIn(SingletonComponent::class)
object ContextModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context.applicationContext
    }
}

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
    fun provideRuinsSearchService(retrofit: Retrofit): RuinsSearchService {
        return retrofit.create(RuinsSearchService::class.java)
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
    @Provides
    @Singleton
    fun providePostQuizAnswerService(retrofit: Retrofit): PostQuizAnswerService{
        return retrofit.create(PostQuizAnswerService::class.java)
    }

    @Provides
    @Singleton
    fun provideRankService(retrofit: Retrofit): RankingService{
        return retrofit.create(RankingService::class.java)
    }

    @Provides
    @Singleton
    fun provideAllCourseService(retrofit: Retrofit): AllCourseService{
        return retrofit.create(AllCourseService::class.java)
    }

    @Provides
    @Singleton
    fun provideRecentCourseService(retrofit: Retrofit): RecentCourseService{
        return retrofit.create(RecentCourseService::class.java)
    }

    @Provides
    @Singleton
    fun provideEventCourseService(retrofit: Retrofit): EventCourseService{
        return retrofit.create(EventCourseService::class.java)
    }
    @Provides
    @Singleton
    fun providePopularCourseService(retrofit: Retrofit): PopularCourseService{
        return retrofit.create(PopularCourseService::class.java)
    }
    @Provides
    @Singleton
    fun provideFcmService(retrofit: Retrofit): FcmService{
        return retrofit.create(FcmService::class.java)
    }
    @Provides
    @Singleton
    fun provideMarketService(retrofit: Retrofit): MarketService{
        return retrofit.create(MarketService::class.java)
    }
    @Provides
    fun provideMarketRepository(service: MarketService): MarketRepository {
        return MarketRepository(service)
    }

    @Provides
    @Singleton
    fun provideCardService(retrofit: Retrofit): CardService{
        return retrofit.create(CardService::class.java)
    }
    @Provides
    @Singleton
    fun provideSearchCourseService(retrofit: Retrofit): SearchCourseService{
        return retrofit.create(SearchCourseService::class.java)
    }
}