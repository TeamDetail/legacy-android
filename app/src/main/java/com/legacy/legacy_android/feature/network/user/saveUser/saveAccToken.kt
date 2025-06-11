package com.legacy.legacy_android.feature.network.user.saveUser

import android.content.Context
import android.util.Log

private const val PREF_NAME = "token_prefs"
private const val KEY_REF_TOKEN = "ref_token"
private const val KEY_ACC_TOKEN = "acc_token"

fun saveRefToken(context: Context, token: String?) {
    try {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        if (token != null) {
            editor.putString(KEY_REF_TOKEN, token)
        } else {
            editor.remove(KEY_REF_TOKEN)
        }
        editor.apply()
    } catch (e: Exception) {
        Log.d("토큰오류", "saveRefToken: $e")
    }
}

fun saveAccToken(context: Context, token: String?) {
    try {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        if (token != null) {
            editor.putString(KEY_ACC_TOKEN, token)
        } else {
            editor.remove(KEY_ACC_TOKEN)
        }
        editor.apply()
    } catch (e: Exception) {
        Log.d("토큰오류", "saveAccToken: $e")
    }
}
