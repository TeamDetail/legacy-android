package com.legacy.legacy_android.feature.screen.home.helper

import com.legacy.legacy_android.feature.network.ruins.RuinsMapResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class RuinsAnimationHelper @Inject constructor(){
    suspend fun updateVisibleRuinsSmoothly(
        currentVisible: List<RuinsMapResponse>,
        newRuins: List<RuinsMapResponse>,
        onUpdate: (List<RuinsMapResponse>) -> Unit,
        chunkSize: Int = 2,
        delayMs: Long = 50L
    ) {
        val currentSet = currentVisible.map { it.ruinsId }.toSet()
        val toAdd = newRuins.filter { it.ruinsId !in currentSet }
        val chunked = toAdd.chunked(chunkSize)

        var updated = currentVisible
        chunked.forEach { chunk ->
            updated = updated + chunk
            onUpdate(updated)
            delay(delayMs)
        }
    }
}