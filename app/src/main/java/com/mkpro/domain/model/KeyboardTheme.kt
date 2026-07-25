package com.mkpro.domain.model

import androidx.annotation.ColorInt

/**
 * Complete theme definition for the mechanical keyboard.
 * Controls all visual aspects of the keyboard appearance.
 */
data class KeyboardTheme(
    val id: String,
    val name: String,
    val isDark: Boolean = true,
    val colors: ThemeColors,
    val keyStyle: KeyStyle,
    val animations: AnimationSettings = AnimationSettings(),
    val soundProfile: SoundProfile? = null
) {
    companion object {
        /**
         * Default dark mechanical theme - Phase 1 primary theme.
         */
        val DARK_MECHANICAL = KeyboardTheme(
            id = "dark_mechanical",
            name = "Dark Mechanical",
            isDark = true,
            colors = ThemeColors(
                background = 0xFF1A1A1E.toInt(),
                keyBackground = 0xFF3D3D42.toInt(),
                keyBackgroundPressed = 0xFF4A4A50.toInt(),
                keyText = 0xFFE0E0E5.toInt(),
                keyTextPressed = 0xFFFFFFFF.toInt(),
                accent = 0xFF6C63FF.toInt(),
                modifierKeyBackground = 0xFF2A2A30.toInt(),
                modifierKeyPressed = 0xFF3A3A42.toInt(),
                actionKeyBackground = 0xFF6C63FF.toInt(),
                actionKeyPressed = 0xFF7B73FF.toInt(),
                border = 0xFF4A4A50.toInt(),
                shadow = 0xCC000000.toInt(),
                ripple = 0x1AFFFFFF.toInt()
            ),
            keyStyle = KeyStyle(
                cornerRadius = 8f,
                elevation = 4f,
                elevationPressed = 1f,
                borderWidth = 0.5f,
                fontSize = 18f,
                iconSize = 22f,
                keySpacing = 3f,
                rowSpacing = 6f
            )
        )

        /**
         * Carbon fiber inspired theme.
         */
        val CARBON = KeyboardTheme(
            id = "carbon",
            name = "Carbon",
            isDark = true,
            colors = ThemeColors(
                background = 0xFF0D0D0D.toInt(),
                keyBackground = 0xFF1F1F1F.toInt(),
                keyBackgroundPressed = 0xFF2A2A2A.toInt(),
                keyText = 0xFFB0B0B0.toInt(),
                keyTextPressed = 0xFFFFFFFF.toInt(),
                accent = 0xFFFF6B35.toInt(),
                modifierKeyBackground = 0xFF151515.toInt(),
                modifierKeyPressed = 0xFF252525.toInt(),
                actionKeyBackground = 0xFFFF6B35.toInt(),
                actionKeyPressed = 0xFFFF8555.toInt(),
                border = 0xFF333333.toInt(),
                shadow = 0xCC000000.toInt(),
                ripple = 0x1AFF6B35.toInt()
            ),
            keyStyle = KeyStyle(
                cornerRadius = 4f,
                elevation = 3f,
                elevationPressed = 0f,
                borderWidth = 1f,
                fontSize = 17f,
                iconSize = 20f,
                keySpacing = 2f,
                rowSpacing = 5f
            )
        )
    }
}

data class ThemeColors(
    @ColorInt val background: Int,
    @ColorInt val keyBackground: Int,
    @ColorInt val keyBackgroundPressed: Int,
    @ColorInt val keyText: Int,
    @ColorInt val keyTextPressed: Int,
    @ColorInt val accent: Int,
    @ColorInt val modifierKeyBackground: Int,
    @ColorInt val modifierKeyPressed: Int,
    @ColorInt val actionKeyBackground: Int,
    @ColorInt val actionKeyPressed: Int,
    @ColorInt val border: Int,
    @ColorInt val shadow: Int,
    @ColorInt val ripple: Int
)

data class KeyStyle(
    val cornerRadius: Float,
    val elevation: Float,
    val elevationPressed: Float,
    val borderWidth: Float,
    val fontSize: Float,
    val iconSize: Float,
    val keySpacing: Float,
    val rowSpacing: Float
)

data class AnimationSettings(
    val pressAnimationEnabled: Boolean = true,
    val rippleEnabled: Boolean = true,
    val rgbEnabled: Boolean = false,
    val keyGlowEnabled: Boolean = false
)

data class SoundProfile(
    val switchType: SwitchType = SwitchType.BLUE,
    val volume: Float = 0.5f,
    val enabled: Boolean = true
)

enum class SwitchType {
    BLUE,   // Clicky, loud
    RED,    // Linear, quiet
    BROWN,  // Tactile, moderate
    BLACK,  // Linear, heavy
    SILENT  // Silent, linear
}
