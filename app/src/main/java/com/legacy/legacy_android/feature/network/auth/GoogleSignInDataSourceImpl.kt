package com.legacy.legacy_android.feature.network.auth

import android.app.Activity
import android.content.Context
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface GoogleSignInDataSource {
    suspend fun signIn(activity: Activity): Result<Credential>
}

class GoogleSignInDataSourceImpl @Inject constructor(
    private val credentialManager: CredentialManager,
    private val googleIdOption: GetGoogleIdOption,
    @ApplicationContext private val context: Context
) : GoogleSignInDataSource {

    override suspend fun signIn(activity: Activity): Result<Credential> {
        return try {
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val response = credentialManager.getCredential(
                request = request,
                context = activity
            )

            val credential = response.credential

            Result.success(credential)
        } catch (e: GetCredentialException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}