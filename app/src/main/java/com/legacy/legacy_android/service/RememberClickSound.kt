package com.legacy.legacy_android.service

import com.legacy.legacy_android.R
import android.media.SoundPool
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
fun RememberClickSound(): Pair<SoundPool, Int> {
    val context = LocalContext.current
    val soundPool = remember {
        SoundPool.Builder().setMaxStreams(1).build()
    }
    val soundId = remember {
        soundPool.load(context, R.raw.click, 1)
    }

    // 해제
    DisposableEffect(Unit) {
        onDispose {
            soundPool.release()
        }
    }

    return Pair(soundPool, soundId)
}
