package com.mkpro.engine

import com.mkpro.domain.model.KeyboardTheme
import com.mkpro.domain.model.KeyType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ThemeEngine manages visual theming for the keyboard.
 * 
 * Responsibilities:
 * - Apply and switch themes
 * - Provide colors for different key types
 * - Handle theme animations
 */
@Singleton
class ThemeEngine @Inject constructor() {

    private val _currentTheme = MutableStateFlow(KeyboardTheme.DARK_MECHANICAL)
    val currentTheme: StateFlow<KeyboardTheme> = _currentTheme.asStateFlow()

    private val availableThemes = mutableListOf(
        KeyboardTheme.DARK_MECHANICAL,
        KeyboardTheme.CARBON
    )

    // ═══════════════════════════════════════════════════════
    // Theme Management
    // ═══════════════════════════════════════════════════════
    fun applyTheme(themeId: String) {
        val theme = availableThemes.find { it.id == themeId }
            ?: KeyboardTheme.DARK_MECHANICAL
        _currentTheme.value = theme
    }

    fun getAvailableThemes(): List<KeyboardTheme> = availableThemes.toList()

    fun addCustomTheme(theme: KeyboardTheme) {
        availableThemes.add(theme)
    }

    // ═══════════════════════════════════════════════════════
    // Key Color Resolution
    // ═══════════════════════════════════════════════════════
    fun getKeyBackgroundColor(keyType: KeyType, isPressed: Boolean): Int {
        val theme = _currentTheme.value
        return when (keyType) {
            KeyType.MODIFIER -> if (isPressed) theme.colors.modifierKeyPressed else theme.colors.modifierKeyBackground
            KeyType.ACTION -> if (isPressed) theme.colors.actionKeyPressed else theme.colors.actionKeyBackground
            KeyType.FUNCTION -> if (isPressed) theme.colors.modifierKeyPressed else theme.colors.modifierKeyBackground
            KeyType.NAVIGATION -> if (isPressed) theme.colors.modifierKeyPressed else theme.colors.modifierKeyBackground
            else -> if (isPressed) theme.colors.keyBackgroundPressed else theme.colors.keyBackground
        }
    }

    fun getKeyTextColor(isPressed: Boolean): Int {
        val theme = _currentTheme.value
        return if (isPressed) theme.colors.keyTextPressed else theme.colors.keyText
    }

    fun getShadowColor(): Int = _currentTheme.value.colors.shadow

    fun getBackgroundColor(): Int = _currentTheme.value.colors.background

    fun getBorderColor(): Int = _currentTheme.value.colors.border

    // ═══════════════════════════════════════════════════════
    // Style Accessors
    // ═══════════════════════════════════════════════════════
    fun getCornerRadius(): Float = _currentTheme.value.keyStyle.cornerRadius

    fun getElevation(isPressed: Boolean): Float {
        return if (isPressed) {
            _currentTheme.value.keyStyle.elevationPressed
        } else {
            _currentTheme.value.keyStyle.elevation
        }
    }

    fun getFontSize(): Float = _currentTheme.value.keyStyle.fontSize

    fun getIconSize(): Float = _currentTheme.value.keyStyle.iconSize

    fun getKeySpacing(): Float = _currentTheme.value.keyStyle.keySpacing

    fun getRowSpacing(): Float = _currentTheme.value.keyStyle.rowSpacing

    fun getBorderWidth(): Float = _currentTheme.value.keyStyle.borderWidth
}
