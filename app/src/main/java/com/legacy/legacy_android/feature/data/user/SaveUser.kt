package com.legacy.legacy_android.feature.data.user

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.runBlocking
import androidx.datastore.preferences.core.stringPreferencesKey
import org.json.JSONObject

val REF_TOKEN = stringPreferencesKey("ref_token")
val ACC_TOKEN = stringPreferencesKey("acc_token")

fun saveRefToken(context: Context, token: String?) {
    runBlocking {
        try {
            context.dataStore.edit { preferences ->

                if (token != null) {
                    preferences[REF_TOKEN] = token
                }
                else {
                    preferences.remove(REF_TOKEN)
                }
            }
        } catch (e: Exception) {
            Log.d("토큰오류", "$e")
        }
    }
}
fun saveAccToken(context: Context, token: String?) {
    runBlocking {
        try {
            context.dataStore.edit { preferences ->
                if (token != null) {
                    preferences[ACC_TOKEN] = token
                }
                else {
                    preferences.remove(ACC_TOKEN)
                }
            }
        } catch (e: Exception) {
            Log.d("토큰오류", "saveAccToken: $e")
        }
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