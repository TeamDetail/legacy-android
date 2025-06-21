package com.legacy.legacy_android.feature.data.user

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.runBlocking
import androidx.datastore.preferences.core.stringPreferencesKey

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