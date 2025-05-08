package com.legacy.legacy_android.feature.screen.ranking

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class RankingViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    val gamemode = listOf("시련", "탐험", "숙련")
    val friendmode = listOf("친구", "전체")

    var gameStatus by mutableStateOf(0)
    var friendStatus by mutableStateOf(0)

}
