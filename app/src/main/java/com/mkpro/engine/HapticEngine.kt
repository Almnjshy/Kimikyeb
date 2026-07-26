package com.mkpro.engine

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.HapticFeedbackConstants
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * HapticEngine provides tactile feedback for key presses.
 * 
 * Uses Android's haptic feedback system with different intensities
 * for different key types.
 */
@Singleton
class HapticEngine @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    private var isEnabled = true
    private var intensity = 0.5f  // 0.0 to 1.0

    fun performFeedback() {
        if (!isEnabled || !vibrator.hasVibrator()) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val effect = VibrationEffect.createPredefined(
                VibrationEffect.EFFECT_CLICK
            )
            vibrator.vibrate(effect)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(10)
        }
    }

    fun performStrongFeedback() {
        if (!isEnabled || !vibrator.hasVibrator()) return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val effect = VibrationEffect.createPredefined(
                VibrationEffect.EFFECT_HEAVY_CLICK
            )
            vibrator.vibrate(effect)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(20)
        }
    }

    fun setEnabled(enabled: Boolean) {
        isEnabled = enabled
    }

    fun setIntensity(value: Float) {
        intensity = value.coerceIn(0f, 1f)
    }
}
