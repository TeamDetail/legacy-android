package com.legacy.legacy_android.feature.screen.market

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MarketViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    private var _marketStatus by mutableStateOf(true)
    val marketStatus: Boolean get() = _marketStatus

    fun setMarketStatus(value: Boolean) {
        _marketStatus = value
    }
}
