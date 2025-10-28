package com.legacy.legacy_android.feature.data.user

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "token")
