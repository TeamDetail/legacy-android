package com.legacy.legacy_android.feature.data.user

import android.content.Context
import androidx.datastore.preferences.core.edit

suspend fun clearToken(context: Context) {
    context.dataStore.edit { preferences ->
        preferences.remove(ACC_TOKEN)
        preferences.remove(REF_TOKEN)
    }
}




