package com.legacy.legacy_android.feature.screen.market

import android.annotation.SuppressLint
import android.icu.util.Calendar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.domain.repository.market.MarketRepository
import com.legacy.legacy_android.feature.network.achieve.CardPack
import com.legacy.legacy_android.feature.screen.market.model.MarketUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    private val marketRepository: MarketRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var timeUntilMidnight by mutableStateOf(getTimeUntilMidnightFormatted())
        private set

    var uiState by mutableStateOf(MarketUiState())
        private set

    init {
        startMidnightCountdown()
    }

    private fun startMidnightCountdown() {
        viewModelScope.launch {
            while (true) {
                timeUntilMidnight = getTimeUntilMidnightFormatted()
                delay(1000)
            }
        }
    }

    fun changePackStatus(status: Int) {
        uiState = uiState.copy(packStatus = status)
    }

    fun changeSuccessBuy(success: Boolean?) {
        uiState = uiState.copy(successBuy = success)
    }

    fun fetchMarketData() {
        viewModelScope.launch {
            val result = marketRepository.getMarketData()
            result.onSuccess { packs ->
                uiState = uiState.copy(packs = packs)
            }.onFailure {
                uiState = uiState.copy(packs = null)
            }
        }
    }

    fun buyCardPack(id: Int, price: Int) {
        viewModelScope.launch {
            changeSuccessBuy(null)
            val result = marketRepository.buyCardPack(id, price)

            changeSuccessBuy(result.isSuccess)
            if (result.isSuccess) {
                launch { userRepository.fetchProfile(force = true) }
                launch { fetchMarketData() }
            }
            delay(3000)
            changeSuccessBuy(null)
        }
    }

    fun setModal() {
        uiState = uiState.copy(isModalOpen = !uiState.isModalOpen)
    }

    fun setCardPack(cardPack: CardPack) {
        uiState = uiState.copy(currentCardPack = cardPack)
    }

    @SuppressLint("DefaultLocale")
    private fun getTimeUntilMidnightFormatted(): String {
        val now = Calendar.getInstance()
        val midnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 24)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        val millis = midnight.timeInMillis - now.timeInMillis
        val seconds = millis / 1000
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }
}
