package com.legacy.legacy_android.feature.network

import com.legacy.legacy_android.R
import com.legacy.legacy_android.ScreenNavigate

data class NavModel(val id: Int, val name: String, val image: Int, val selImage: Int, val onClick: ScreenNavigate)

object Nav {
    private var _navStatus = 2

    fun setNavStatus(newStatus: Int) {
        _navStatus = newStatus
    }

    fun getNavStatus(): Int {
        return _navStatus
    }

    val navList = listOf(
        NavModel(id = 0, name = "상점", image = R.drawable.shop, selImage = R.drawable.p_shop, onClick = ScreenNavigate.MARKET),
        NavModel(id = 1, name = "도전과제", image = R.drawable.medal, selImage = R.drawable.p_medal, onClick = ScreenNavigate.ACHIEVE),
        NavModel(id = 2, name = "탐험", image = R.drawable.flag, selImage = R.drawable.p_flag, onClick = ScreenNavigate.HOME),
        NavModel(id = 3, name = "시련", image = R.drawable.fight, selImage = R.drawable.p_fight, onClick = ScreenNavigate.TRIAL),
        NavModel(id = 4, name = "랭킹", image = R.drawable.trophy, selImage = R.drawable.p_trophy, onClick = ScreenNavigate.RANKING)
    )
}
