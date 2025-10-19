package com.legacy.legacy_android.res.component.modal.check

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.CheckRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckViewModel @Inject constructor(
    private val checkRepository: CheckRepository
): ViewModel(){
    var uiState by mutableStateOf(CheckUiState())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun changeCheckStatus(status: Int) {
        uiState = uiState.copy(checkStatus = status)
    }

    fun checkDaily(){
        viewModelScope.launch {
            isLoading = true
            try {
                checkRepository.checkDaily()
            } catch (e: Exception) {
                Log.e("CheckViewModel",  " 출첵 수신 실패", e)
            }
            isLoading = false
        }
    }

    fun getItems() {
        viewModelScope.launch {
            try {
                val result = checkRepository.getItems()
                if (result.isSuccess) uiState = uiState.copy(getItems = result)
            } catch (e: Exception) {
                Log.e("MailViewModel", "아이템 수령 실패", e)
            }
        }
    }
}