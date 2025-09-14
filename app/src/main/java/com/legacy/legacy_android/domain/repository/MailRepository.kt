package com.legacy.legacy_android.domain.repository

import com.legacy.legacy_android.feature.network.mail.MailResponse
import com.legacy.legacy_android.feature.network.mail.MailService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MailRepository @Inject constructor(
    private val mailService: MailService
){
    suspend fun getMails(): Result<List<MailResponse>?> {
        return try{
            val response = mailService.getMails()
            Result.success(response)
        }catch(e: Exception){

            Result.failure(e)
        }
    }
    suspend fun getItems(): Result<List<MailResponse>?> {
        return try{
            val response = mailService.getItems()
            Result.success(response)
        }catch(e: Exception){

            Result.failure(e)
        }
    }

}