package com.legacy.legacy_android.feature.screen.home

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application
): AndroidViewModel(application){

    private val _isTabClicked = mutableStateOf<Boolean>(false)

    fun setTabClicked(): Boolean {
        _isTabClicked.value = !_isTabClicked.value
        return _isTabClicked.value
    }

    fun getTabClicked(): Boolean{
        return _isTabClicked.value
    }


}