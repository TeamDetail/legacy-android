package com.legacy.legacy_android.domain.repository

import android.app.Activity
import android.credentials.Credential
interface GoogleSignInRepository {
    suspend fun signIn(activity: Activity): Result<Credential>
}