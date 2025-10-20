package com.legacy.legacy_android.res.component.modal.check

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.CheckRepository
import com.legacy.legacy_android.feature.network.user.InventoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CheckViewModel @Inject constructor(
    private val checkRepository: CheckRepository
): ViewModel() {
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

    fun setSelectedCheck(award: List<InventoryItem>){
        uiState = uiState.copy(currentCheck = award)
    }

    fun setSelectedDay(day: Int) {
        uiState = uiState.copy(currentDay = day)
    }



    fun getItem() {
        viewModelScope.launch {
            try {
                val today = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                checkDaily()
                val todayCheck = uiState.check?.find { daily ->
                    try {
                        val start = LocalDate.parse(daily.startAt, formatter)
                        val end = LocalDate.parse(daily.endAt, formatter)
                        !today.isBefore(start) && !today.isAfter(end)
                    } catch (e: Exception) {
                        Log.e("CheckViewModel", "날짜 파싱 실패", e)
                        false
                    }
                }

                if (todayCheck != null) {
                    val response = checkRepository.getItem(todayCheck.id)
                    if (response.isSuccess) {
                        uiState = uiState.copy(
                            received = true,
                            getItems = response.getOrNull()
                        )
                    } else {
                        uiState = uiState.copy(received = false)
                        Log.e("CheckViewModel", "아이템 수령 실패", response.exceptionOrNull())
                    }
                } else {
                    Log.w("CheckViewModel", "오늘에 해당하는 출석 데이터 없음 → getItem 실행 안 함")
                }

            } catch (e: Exception) {
                Log.e("CheckViewModel", "아이템 수령 중 예외 발생", e)
            }

            delay(3000)
            uiState = uiState.copy(received = null)
        }
    }
}
