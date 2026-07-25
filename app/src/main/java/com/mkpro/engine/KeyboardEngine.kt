package com.mkpro.engine

import android.view.KeyEvent
import android.view.inputmethod.InputConnection
import com.mkpro.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Core keyboard engine that processes all key events.
 * 
 * Responsibilities:
 * - Key press/release handling
 * - Modifier key state tracking (Shift, Ctrl, Alt)
 * - Character transformation based on modifiers
 * - InputConnection communication
 * - Desktop key event dispatching
 */
@Singleton
class KeyboardEngine @Inject constructor(
    private val layoutEngine: LayoutEngine,
    private val soundEngine: SoundEngine,
    private val hapticEngine: HapticEngine
) {
    // ═══════════════════════════════════════════════════════
    // State Management
    // ═══════════════════════════════════════════════════════
    private val _keyState = MutableStateFlow<KeyState>(KeyState.Idle)
    val keyState: StateFlow<KeyState> = _keyState.asStateFlow()

    private val _activeModifiers = MutableStateFlow<Set<ModifierKey>>(emptySet())
    val activeModifiers: StateFlow<Set<ModifierKey>> = _activeModifiers.asStateFlow()

    private val _isShiftLocked = MutableStateFlow(false)
    val isShiftLocked: StateFlow<Boolean> = _isShiftLocked.asStateFlow()

    // Current input connection - set by the IME service
    private var inputConnection: InputConnection? = null

    // Currently pressed keys (for multi-touch)
    private val pressedKeys = mutableSetOf<String>()

    // ═══════════════════════════════════════════════════════
    // Input Connection Management
    // ═══════════════════════════════════════════════════════
    fun setInputConnection(connection: InputConnection?) {
        inputConnection = connection
    }

    // ═══════════════════════════════════════════════════════
    // Key Event Processing
    // ═══════════════════════════════════════════════════════
    fun onKeyPress(key: KeyDefinition) {
        pressedKeys.add(key.id)

        // Trigger feedback
        soundEngine.playKeySound(key)
        hapticEngine.performFeedback()

        when (key.type) {
            KeyType.CHARACTER -> handleCharacterKey(key)
            KeyType.MODIFIER -> handleModifierPress(key)
            KeyType.ACTION -> handleActionKey(key)
            KeyType.NAVIGATION -> handleNavigationKey(key)
            KeyType.FUNCTION -> handleFunctionKey(key)
            KeyType.SPECIAL -> handleSpecialKey(key)
            else -> { /* Not implemented in Phase 1 */ }
        }

        _keyState.value = KeyState.Pressed(key)
    }

    fun onKeyRelease(key: KeyDefinition) {
        pressedKeys.remove(key.id)

        when (key.type) {
            KeyType.MODIFIER -> handleModifierRelease(key)
            else -> { /* No special handling needed */ }
        }

        _keyState.value = KeyState.Released(key)
    }

    // ═══════════════════════════════════════════════════════
    // Character Key Handling
    // ═══════════════════════════════════════════════════════
    private fun handleCharacterKey(key: KeyDefinition) {
        val ic = inputConnection ?: return

        val modifiers = _activeModifiers.value
        val isShiftActive = modifiers.contains(ModifierKey.SHIFT) || _isShiftLocked.value

        // Determine the character to send
        val charToSend = when {
            isShiftActive && key.shiftCharacter != null -> key.shiftCharacter
            else -> key.character
        } ?: return

        // Handle Ctrl combinations
        if (modifiers.contains(ModifierKey.CTRL)) {
            handleCtrlCombination(charToSend)
            return
        }

        // Send character to input connection
        ic.commitText(charToSend.toString(), 1)

        // Auto-release shift after single character (unless locked)
        if (isShiftActive && !_isShiftLocked.value) {
            _activeModifiers.value = modifiers - ModifierKey.SHIFT
        }
    }

    // ═══════════════════════════════════════════════════════
    // Modifier Key Handling
    // ═══════════════════════════════════════════════════════
    private fun handleModifierPress(key: KeyDefinition) {
        val modifier = when (key.id) {
            "shift", "shift_right" -> ModifierKey.SHIFT
            "ctrl" -> ModifierKey.CTRL
            "alt" -> ModifierKey.ALT
            "fn" -> ModifierKey.FN
            else -> return
        }

        val current = _activeModifiers.value.toMutableSet()

        // Double-tap shift for caps lock
        if (modifier == ModifierKey.SHIFT && current.contains(ModifierKey.SHIFT)) {
            _isShiftLocked.value = !_isShiftLocked.value
            if (!_isShiftLocked.value) {
                current.remove(ModifierKey.SHIFT)
            }
        } else {
            current.add(modifier)
        }

        _activeModifiers.value = current
    }

    private fun handleModifierRelease(key: KeyDefinition) {
        val modifier = when (key.id) {
            "shift", "shift_right" -> ModifierKey.SHIFT
            "ctrl" -> ModifierKey.CTRL
            "alt" -> ModifierKey.ALT
            "fn" -> ModifierKey.FN
            else -> return
        }

        // Don't release shift if locked
        if (modifier == ModifierKey.SHIFT && _isShiftLocked.value) return

        val current = _activeModifiers.value.toMutableSet()
        current.remove(modifier)
        _activeModifiers.value = current
    }

    // ═══════════════════════════════════════════════════════
    // Action Key Handling
    // ═══════════════════════════════════════════════════════
    private fun handleActionKey(key: KeyDefinition) {
        val ic = inputConnection ?: return

        when (key.id) {
            "backspace" -> ic.deleteSurroundingText(1, 0)
            "enter" -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
            "tab" -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_TAB))
            "esc" -> ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ESCAPE))
            "space" -> ic.commitText(" ", 1)
        }
    }

    // ═══════════════════════════════════════════════════════
    // Navigation Key Handling
    // ═══════════════════════════════════════════════════════
    private fun handleNavigationKey(key: KeyDefinition) {
        val ic = inputConnection ?: return
        val keyCode = key.keyCode ?: return

        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, keyCode))
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, keyCode))
    }

    // ═══════════════════════════════════════════════════════
    // Function Key Handling
    // ═══════════════════════════════════════════════════════
    private fun handleFunctionKey(key: KeyDefinition) {
        val ic = inputConnection ?: return
        val keyCode = key.keyCode ?: return

        // Send function key events
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, keyCode))
        ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, keyCode))
    }

    // ═══════════════════════════════════════════════════════
    // Special Key Handling
    // ═══════════════════════════════════════════════════════
    private fun handleSpecialKey(key: KeyDefinition) {
        when (key.id) {
            "symbols" -> layoutEngine.switchToSymbolsLayout()
            "letters" -> layoutEngine.switchToLettersLayout()
            "emoji" -> { /* Phase 2+ */ }
            "language" -> { /* Phase 2+ */ }
            "settings" -> { /* Open settings */ }
        }
    }

    // ═══════════════════════════════════════════════════════
    // Ctrl Combination Handling
    // ═══════════════════════════════════════════════════════
    private fun handleCtrlCombination(char: Char) {
        val ic = inputConnection ?: return

        val keyCode = when (char.lowercaseChar()) {
            'a' -> KeyEvent.KEYCODE_A
            'c' -> KeyEvent.KEYCODE_C
            'v' -> KeyEvent.KEYCODE_V
            'x' -> KeyEvent.KEYCODE_X
            'z' -> KeyEvent.KEYCODE_Z
            'y' -> KeyEvent.KEYCODE_Y
            else -> return
        }

        // Send Ctrl+Key combination
        val eventTime = System.currentTimeMillis()
        ic.sendKeyEvent(KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, 
            KeyEvent.KEYCODE_CTRL_LEFT, 0, KeyEvent.META_CTRL_ON))
        ic.sendKeyEvent(KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, 
            keyCode, 0, KeyEvent.META_CTRL_ON))
        ic.sendKeyEvent(KeyEvent(eventTime, eventTime, KeyEvent.ACTION_UP, 
            keyCode, 0, KeyEvent.META_CTRL_ON))
        ic.sendKeyEvent(KeyEvent(eventTime, eventTime, KeyEvent.ACTION_UP, 
            KeyEvent.KEYCODE_CTRL_LEFT, 0))
    }

    // ═══════════════════════════════════════════════════════
    // Utility Methods
    // ═══════════════════════════════════════════════════════
    fun isKeyPressed(keyId: String): Boolean = pressedKeys.contains(keyId)

    fun resetState() {
        pressedKeys.clear()
        _activeModifiers.value = emptySet()
        _isShiftLocked.value = false
        _keyState.value = KeyState.Idle
    }

    fun releaseAllModifiers() {
        _activeModifiers.value = emptySet()
        _isShiftLocked.value = false
    }
}

// ═══════════════════════════════════════════════════════════
// Supporting Classes
// ═══════════════════════════════════════════════════════════
sealed class KeyState {
    object Idle : KeyState()
    data class Pressed(val key: KeyDefinition) : KeyState()
    data class Released(val key: KeyDefinition) : KeyState()
}

enum class ModifierKey {
    SHIFT, CTRL, ALT, FN
}
