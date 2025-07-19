package com.legacy.legacy_android.feature.screen.market

import android.app.Application
import android.icu.util.Calendar
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.legacy.legacy_android.feature.screen.market.model.MarketUiState

class MarketViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    var timeUntilMidnight by mutableStateOf(getTimeUntilMidnightFormatted())
        private set

    var uiState by mutableStateOf(MarketUiState())
        private set

    fun changePackStatus(status: Int){
        uiState = uiState.copy(packStatus = status)
    }

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
