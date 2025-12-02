package com.legacy.legacy_android.feature.screen.event

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.legacy.legacy_android.domain.repository.event.EventRepository
import com.legacy.legacy_android.feature.network.event.EventResponse
import com.legacy.legacy_android.feature.screen.event.model.EventUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor (
    private val eventRepository: EventRepository
): ViewModel(){
    var uiState by mutableStateOf(EventUiState())
        private set
    fun getAllEvents() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            eventRepository.getAllEvents()
                .onSuccess { eventList ->
                    uiState = uiState.copy(
                        eventList = eventList,
                        isLoading = false
                    )
                }
                .onFailure {
                    uiState = uiState.copy(isLoading = false)
                }
        }
    }

    fun getEventById(eventId: Int, navigate: () -> Unit) {
        viewModelScope.launch {
            eventRepository.getEventById(eventId)
                .onSuccess { event ->
                    uiState = uiState.copy(
                        currentEvent = event
                    )
                    navigate()
                }
                .onFailure {
                    uiState = uiState.copy(isLoading = false)
                }
        }
    }
}