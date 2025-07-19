package com.legacy.legacy_android.domain.repository

import android.content.Context
import com.legacy.legacy_android.feature.data.user.saveAccToken
import com.legacy.legacy_android.feature.data.user.saveRefToken
import javax.inject.Inject

interface TokenRepository {
    suspend fun saveTokens(accessToken: String, refreshToken: String)
}

class TokenRepositoryImpl @Inject constructor(
    private val context: Context
) : TokenRepository {

    override suspend fun saveTokens(accessToken: String, refreshToken: String) {
        saveAccToken(context, accessToken)
        saveRefToken(context, refreshToken)
    }
}