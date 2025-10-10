package com.legacy.legacy_android.feature.network.login

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppleSignInDataSource @Inject constructor() {

    suspend fun loginWithApple(activity: Activity, clientId: String, redirectUri: String): String {
        return suspendCoroutine { continuation ->
            val url = Uri.Builder()
                .scheme("https")
                .authority("appleid.apple.com")
                .appendPath("auth")
                .appendPath("authorize")
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("client_id", clientId)
                .appendQueryParameter("redirect_uri", redirectUri)
                .appendQueryParameter("scope", "name email")
                .build()

            val intent = Intent(Intent.ACTION_VIEW, url)
            activity.startActivity(intent)

        }
    }
}
