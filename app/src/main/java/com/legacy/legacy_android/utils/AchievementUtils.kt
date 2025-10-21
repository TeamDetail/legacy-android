package com.legacy.legacy_android.utils

import com.legacy.legacy_android.R

object AchievementMapper {

    fun getBackgroundRes(grade: String?): Int {
        return when (grade) {
            "LEGENDARY" -> R.drawable.legendary
            "EPIC" -> R.drawable.epic
            "UNIQUE" -> R.drawable.unique
            "CHALLENGE" -> R.drawable.challenge
            "COMMON" -> R.drawable.common
            else -> R.drawable.legacylogo
        }
    }

    fun getItemRes(type: String?): Int? {
        return when(type) {
            "CARD" -> R.drawable.card
            "SHINING_CARD" -> R.drawable.shining_card
            "CARD_PACK" -> R.drawable.card_pack
            "STATED_CARD" -> R.drawable.shining_card
            "RUINS" -> R.drawable.ruins
            "BLOCKS" -> R.drawable.blocks
            "CLEAR_COURSE" -> R.drawable.clear_course
            "MAKE_COURSE" -> R.drawable.make_course
            "STATE_COURSE" -> R.drawable.make_course
            "SOLVE_QUIZ" -> R.drawable.solve_quiz
            "WRONG_QUIZ" -> R.drawable.wrong_quiz
            "BUY_ITEM" -> R.drawable.buy_item
            "TITLE" -> R.drawable.sequence_present
            "LEVEL" -> R.drawable.level
            "FRIEND" -> R.drawable.friend
            else -> null
        }
    }


    fun getReward(grade: String?): Pair<Int, Int> {
        return when(grade) {
            "LEGENDARY" -> 500 to 500_000
            "EPIC" -> 250 to 50_000
            "UNIQUE" -> 150 to 10_000
            "CHALLENGE" -> 100 to 5_000
            "COMMON" -> 50 to 500
            else -> 0 to 0
        }
    }

}
