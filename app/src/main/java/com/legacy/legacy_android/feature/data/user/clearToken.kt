package com.legacy.legacy_android.feature.data.user

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.datastore.preferences.core.edit


suspend fun clearToken(context: Context) {
    Log.e("TOKEN_CLEAR", "===== clearToken 호출됨 =====")
    Log.e("TOKEN_CLEAR", "호출 위치:", Exception("Stack trace"))

    context.dataStore.edit { prefs ->
        prefs.remove(ACC_TOKEN)
        prefs.remove(REF_TOKEN)
    }

    Log.e("TOKEN_CLEAR", "after clear: acc_token, ref_token removed from DataStore")
    Log.e("TOKEN_CLEAR", "토큰 삭제 완료")
}
