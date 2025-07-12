package com.legacy.legacy_android.feature.data.user

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.json.JSONObject

fun getAccToken(context: Context): String? {
    return runBlocking {
        val preferences = context.dataStore.data.first()
        val currentToken = preferences[ACC_TOKEN]

        if (!currentToken.isNullOrBlank() && isTokenValidInternal(currentToken)) {
            return@runBlocking currentToken
        }

        val refreshToken = preferences[REF_TOKEN]
        if (refreshToken.isNullOrBlank()) {
            return@runBlocking null
        }

        try {
            val newToken = refreshAccessToken(context, refreshToken)
            if (newToken != null && isTokenValidInternal(newToken)) {
                saveAccToken(context, newToken)
                return@runBlocking newToken
            }
        } catch (e: Exception) {
            Log.e("토큰갱신", "자동 갱신 실패: $e")
        }

        return@runBlocking null
    }
}

private fun isTokenValidInternal(token: String): Boolean {
    return try {
        val parts = token.split(".")
        if (parts.size != 3) return false

        val payload = String(
            android.util.Base64.decode(
                parts[1],
                android.util.Base64.URL_SAFE or android.util.Base64.NO_PADDING
            )
        )

        val json = JSONObject(payload)
        val exp = json.getLong("exp")
        val now = System.currentTimeMillis() / 1000

        now < exp
    } catch (e: Exception) {
        Log.e("Token", "토큰 검증 실패: $e")
        false
    }
}

fun isTokenValid(token: String?): Boolean {
    if (token.isNullOrBlank()) return false
    return isTokenValidInternal(token)
}
fun getRefToken(context: Context): String? {
    return runBlocking {
        val preferences = context.dataStore.data.first()
        preferences[REF_TOKEN]
    }
}

suspend fun refreshAccessToken(context: Context, refreshToken: String): String? {
    return try {
        null
    } catch (e: Exception) {
        Log.e("토큰갱신", "refreshAccessToken 오류: $e")
        null
    }
}