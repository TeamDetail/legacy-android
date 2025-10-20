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

    fun checkDaily() {
        viewModelScope.launch {
            val result = checkRepository.checkDaily()
            result.onSuccess {
                uiState = uiState.copy(check = it)
            }.onFailure {
                Log.e("CheckViewModel", "출첵 수신 실패", it)
            }
        }
    }

    fun setSelectedItem(id: Int) {
        val currentCheck = uiState.check?.get(id)
        uiState = uiState.copy(selectedCheck = currentCheck)
    }

    fun getItem(){
        viewModelScope.launch {
            try{
                val response = checkRepository.getItem(uiState.selectedCheck!!.id)
                if(response.isSuccess){
                    uiState = uiState.copy(getItems = response.getOrNull())
                }else{
                    Log.e("CheckViewModel",  " 아이템 수령 실패", response.exceptionOrNull())
                }
            } catch (e: Exception){
                Log.e("CheckViewModel",  " 아이템 수령 실패", e)
            }
        }
    }
}