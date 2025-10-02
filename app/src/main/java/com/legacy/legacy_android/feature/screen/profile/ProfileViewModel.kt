package com.legacy.legacy_android.feature.screen.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.legacy.legacy_android.domain.repository.UserRepository
import com.legacy.legacy_android.domain.repository.card.CardRepository
import com.legacy.legacy_android.feature.network.user.InventoryItem
import com.legacy.legacy_android.feature.network.user.UserData
import com.legacy.legacy_android.feature.screen.profile.model.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val cardRepository: CardRepository
) : ViewModel() {
    val profileFlow = userRepository.profile

    var profile by mutableStateOf<UserData?>(null)
        private set


    var uiState by mutableStateOf(ProfileUiState())
        private set

    fun updateCardPackOpen(status: Boolean){
        uiState = uiState.copy(cardPackOpen = status)
    }

    fun decreasePackOpenCount(count: Int){
        uiState = if (uiState.packOpenCount < 2){
            uiState.copy(packOpenCount = uiState.selectedItem!!.itemCount)
        }else {
            uiState.copy(packOpenCount = uiState.packOpenCount - count)
        }
    }

    fun increasePackOpenCount(count: Int){
        uiState = if (uiState.packOpenCount + count > uiState.selectedItem!!.itemCount){
            uiState.copy(packOpenCount = uiState.selectedItem!!.itemCount)
        }else{
            uiState.copy(packOpenCount = uiState.packOpenCount + count)
        }
    }

    fun initCardPackOpenCount(){
        uiState = uiState.copy(packOpenCount = 1)
    }

    fun changeProfileStatus(status: Int){
        uiState = uiState.copy(profileStatus = status)
    }

    fun changeTitleStatus(status: Int){
        uiState = uiState.copy(titleStatus = status)
    }

    fun fetchProfile(force: Boolean = false) {
        viewModelScope.launch {
            userRepository.fetchProfile(force)
            userRepository.profile.collect { data ->
                profile = data
            }
        }
    }


    fun clearProfile() {
        userRepository.clearProfile()
    }

    fun fetchMyInventory(){
        viewModelScope.launch {
            val result = userRepository.getInventory()
            result.onSuccess {
                Log.d("UserRepository", "인벤토리 불러오기 성공: $it")
                uiState = uiState.copy(
                    myInventory = it
                )
            }
        }
    }

    fun setSelectedItem(item: InventoryItem?){
        uiState = uiState.copy(selectedItem = item)
        println(uiState.selectedItem)
    }

    fun fetchMyCollection() {
        viewModelScope.launch {
            val cards = uiState.statusList.map { status ->
                async { cardRepository.fetchMyCard(status) }
            }.awaitAll()

            val successCards = cards.mapNotNull { it.getOrNull() }
            uiState = uiState.copy(myCards = successCards)
        }
    }

    fun initCardPack(){
        uiState = uiState.copy(
            openCardResponse = null
        )
    }

    fun openCardPack(){
        viewModelScope.launch {
            fetchMyInventory()
            val result = userRepository.openCardPack(id = uiState.selectedItem!!.itemId, count = uiState.packOpenCount)
            result.onSuccess {
                Log.d("UserRepository", "카드팩 열기 성공: $it")
                uiState = uiState.copy(
                    openCardResponse = it
                )
            }.onFailure {
                e-> Log.e("UserRepository", "카드팩 열기 실패", e)
            }
        }
    }

    fun getTitles(){
        viewModelScope.launch {
            val result = userRepository.getTitles()
            result.onSuccess {
                Log.d("UserRepository", "칭호 불러오기 성공: $it")
                uiState = uiState.copy(
                    titleList = it
                )
            }
        }
    }
}
