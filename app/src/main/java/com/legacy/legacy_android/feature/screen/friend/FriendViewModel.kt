package com.legacy.legacy_android.feature.screen.friend

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.feature.screen.profile.model.ProfilePendingUiState
import com.legacy.legacy_android.feature.screen.profile.model.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class FriendViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {
}