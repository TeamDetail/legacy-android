package com.legacy.legacy_android.feature.usecase

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.map
import javax.inject.Singleton

@Singleton
class DataStoreManager(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "settings")

    private val KAKAO_TOKEN = stringPreferencesKey("kakao_token")

    private val GOOGLE_TOKEN = stringPreferencesKey("google_token")

    suspend fun saveKakaoToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[KAKAO_TOKEN] = token
        }
    }

    suspend fun saveGoogleIdToken(token: String) {
        context.dataStore.edit {
            prefs-> prefs[GOOGLE_TOKEN] = token
        }
    }
}