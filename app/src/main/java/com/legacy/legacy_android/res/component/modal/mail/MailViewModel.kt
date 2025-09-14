package com.legacy.legacy_android.res.component.modal.mail


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.MailRepository
import com.legacy.legacy_android.feature.network.mail.MailResponse
import com.legacy.legacy_android.feature.network.mail.MailService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.legacy.legacy_android.BuildConfig

class MailViewModel : ViewModel() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.SERVER_API_KEY)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val mailService = retrofit.create(MailService::class.java)
    private val mailRepository = MailRepository(mailService)

    var mails by mutableStateOf<List<MailResponse>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadMails() {
        viewModelScope.launch {
            isLoading = true
            val result = mailRepository.getMails()
            mails = result.getOrNull() ?: emptyList()
            isLoading = false
        }
    }

    fun getItems(){
        viewModelScope.launch {
            val result = mailRepository.getItems()
            loadMails()
        }
    }
}
