package com.legacy.legacy_android.feature.network.auth

import android.app.Activity
import androidx.credentials.Credential
import com.legacy.legacy_android.domain.repository.GoogleSignInRepository
import javax.inject.Inject

class GoogleSignInRepositoryImpl @Inject constructor(
    private val googleSignInDataSource: GoogleSignInDataSource
): GoogleSignInRepository {
    override suspend fun signIn(activity: Activity): Result<Credential> =
        googleSignInDataSource.signIn(activity)
}