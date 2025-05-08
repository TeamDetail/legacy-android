package com.legacy.legacy_android.res.component.bars.infobar

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import javax.inject.Inject

class InfoBarViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
    var isTabClicked by mutableStateOf(false)
    var name by mutableStateOf("박재민")
    var level by mutableStateOf(99)
    var money by mutableStateOf(6506246420)

    fun setIsTabClicked(){
        isTabClicked = !isTabClicked;
    }
}