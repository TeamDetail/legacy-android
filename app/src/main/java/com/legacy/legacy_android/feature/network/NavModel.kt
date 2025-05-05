package com.legacy.legacy_android.feature.network

import androidx.compose.runtime.mutableStateOf
import com.legacy.legacy_android.R

data class NavModel(val id: Int, val name: String, val image: Int, val selImage: Int)

object Nav {
    private var _navStatus = mutableStateOf(2)

    fun setNavStatus(newStatus: Int) {
        _navStatus.value = newStatus
    }

    fun getNavStatus(): Int {
        return _navStatus.value
    }

    val navList = listOf(
        NavModel(id = 0, name = "상점", image = R.drawable.shop, selImage = R.drawable.p_shop),
        NavModel(id = 1, name = "도전과제", image = R.drawable.medal, selImage = R.drawable.p_medal),
        NavModel(id = 2, name = "탐험", image = R.drawable.flag, selImage = R.drawable.p_flag),
        NavModel(id = 3, name = "시련", image = R.drawable.fight, selImage = R.drawable.p_fight),
        NavModel(id = 4, name = "랭킹", image = R.drawable.trophy, selImage = R.drawable.p_trophy)
    )


}
