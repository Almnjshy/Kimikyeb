package com.mkpro.engine

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import com.mkpro.domain.model.KeyDefinition
import com.mkpro.domain.model.KeyType
import com.mkpro.domain.model.SoundProfile
import com.mkpro.domain.model.SwitchType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * SoundEngine provides mechanical keyboard sound effects.
 * 
 * Supports different switch types:
 * - BLUE: Clicky, loud (classic mechanical)
 * - RED: Linear, quiet
 * - BROWN: Tactile, moderate
 * - BLACK: Linear, heavy
 * - SILENT: Silent, linear
 */
@Singleton
class SoundEngine @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var soundPool: SoundPool? = null
    private var keyPressSoundId: Int = 0
    private var keyReleaseSoundId: Int = 0
    private var modifierSoundId: Int = 0
    private var enterSoundId: Int = 0
    private var backspaceSoundId: Int = 0

    private var _soundProfile = SoundProfile()

    init {
        initializeSoundPool()
    }

    private fun initializeSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(audioAttributes)
            .build()

        // Load sounds from raw resources (will be added in resources)
        // For Phase 1, we use system sounds as fallback
    }

    fun playKeySound(key: KeyDefinition) {
        if (!_soundProfile.enabled) return

        val volume = _soundProfile.volume

        when (key.type) {
            KeyType.MODIFIER -> playModifierSound(volume)
            KeyType.ACTION -> when (key.id) {
                "enter" -> playEnterSound(volume)
                "backspace" -> playBackspaceSound(volume)
                else -> playGenericSound(volume)
            }
            else -> playGenericSound(volume)
        }
    }

    private fun playGenericSound(volume: Float) {
        // Use system click sound as fallback for Phase 1
        // In Phase 3, load actual mechanical switch sounds
    }

    private fun playModifierSound(volume: Float) {
        // Modifier keys have deeper sound
    }

    private fun playEnterSound(volume: Float) {
        // Enter key has satisfying thock sound
    }

    private fun playBackspaceSound(volume: Float) {
        // Backspace has distinct click sound
    }

    fun setSoundProfile(profile: SoundProfile) {
        _soundProfile = profile
    }

    fun isEnabled(): Boolean = _soundProfile.enabled

    fun release() {
        soundPool?.release()
        soundPool = null
    }
}
