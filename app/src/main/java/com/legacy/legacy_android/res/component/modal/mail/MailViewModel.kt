package com.legacy.legacy_android.res.component.modal.mail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.MailRepository
import com.legacy.legacy_android.feature.network.mail.ItemData
import com.legacy.legacy_android.feature.network.mail.MailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MailViewModel @Inject constructor(
    private val mailRepository: MailRepository
) : ViewModel() {

    var mails by mutableStateOf<List<MailResponse>>(emptyList())
        private set

    var items by mutableStateOf<List<ItemData>>(emptyList())

    var isLoading by mutableStateOf(false)
        private set

    var currentItem by mutableStateOf<MailResponse?>(null)

    fun loadMails() {
        viewModelScope.launch {
            isLoading = true
            try {
                val result = mailRepository.getMails()
                mails = result.getOrNull() ?: emptyList()
                Log.d("MailViewModel", "메일 수신: ${mails.size}개")
            } catch (e: Exception) {
                Log.e("MailViewModel", "메일 수신 실패", e)
            }
            isLoading = false
        }
    }

    fun getItems() {
        viewModelScope.launch {
            try {
                items = mails.flatMap { it.itemData }
                mailRepository.getItems()
                loadMails()
            } catch (e: Exception) {
                Log.e("MailViewModel", "아이템 수령 실패", e)
            }
        }
    }
}
