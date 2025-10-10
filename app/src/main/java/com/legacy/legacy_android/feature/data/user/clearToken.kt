package com.legacy.legacy_android.feature.data.user

import android.content.Context
import android.util.Log
import androidx.core.content.edit

fun clearToken(context: Context) {
    Log.e("TOKEN_CLEAR", "===== clearToken 호출됨 =====")
    Log.e("TOKEN_CLEAR", "호출 위치:", Exception("Stack trace"))

    context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        .edit {
            remove("access_token")
                .remove("refresh_token")
        }

    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    Log.e("TOKEN_CLEAR", "after clear: access_token=${prefs.getString("access_token", "null")}")
    Log.e("TOKEN_CLEAR", "토큰 삭제 완료")
}