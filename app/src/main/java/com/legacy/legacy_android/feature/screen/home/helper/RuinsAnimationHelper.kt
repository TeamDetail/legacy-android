package com.legacy.legacy_android.feature.screen.home.helper

import com.legacy.legacy_android.feature.network.ruins.RuinsMapResponse
import kotlinx.coroutines.delay
import javax.inject.Inject

class RuinsAnimationHelper @Inject constructor() {
    suspend fun updateVisibleRuinsSmoothly(
        currentVisible: List<RuinsMapResponse>,
        newRuins: List<RuinsMapResponse>,
        onUpdate: (List<RuinsMapResponse>) -> Unit
    ) {
        val newRuinsSet = newRuins.map { it.ruinsId }.toSet()

        val stillVisible = currentVisible.filter { it.ruinsId in newRuinsSet }

        val currentSet = stillVisible.map { it.ruinsId }.toSet()
        val toAdd = newRuins.filter { it.ruinsId !in currentSet }
        val chunked = toAdd.chunked(2)

        var updated = stillVisible
        chunked.forEach { chunk ->
            updated = updated + chunk
            onUpdate(updated)
            delay(50)
        }
    }
}