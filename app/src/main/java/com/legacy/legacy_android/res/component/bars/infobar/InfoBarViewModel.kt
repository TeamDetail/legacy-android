package com.legacy.legacy_android.res.component.bars.infobar

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.feature.network.user.GetMeService
import com.legacy.legacy_android.feature.network.user.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoBarViewModel @Inject constructor(
    application: Application,
    @ApplicationContext private val context: Context,
    private val getMeService: GetMeService

) : AndroidViewModel(application) {
    var isTabClicked by mutableStateOf(false)
    fun setIsTabClicked(){
        isTabClicked = !isTabClicked;
    }

    var profile by mutableStateOf<UserData?>(null)
        private set

    fun fetchProfile() {
        viewModelScope.launch {
            try {
                val response = getMeService.getMe()
                profile = response.data
            } catch (error: Error) {
                Log.e("프로필 뷰에서 에러발생", error.toString())
            }
        }
    }
}