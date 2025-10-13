package com.legacy.legacy_android.feature.network.auth

import android.app.Activity
import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.legacy.legacy_android.BuildConfig
import com.legacy.legacy_android.domain.repository.GoogleSignInRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GoogleSignInRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : GoogleSignInRepository {

    override suspend fun signIn(activity: Activity): Result<Credential> {
        return try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.ANDROID_WEBCLIENT_KEY)
                .setAutoSelectEnabled(true)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val credentialManager = CredentialManager.create(context)
            val result = credentialManager.getCredential(
                request = request,
                context = activity
            )

            Result.success(result.credential)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}