package com.mkpro.service

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import com.mkpro.engine.*
import com.mkpro.service.inputview.InputContainer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * MKProInputMethodService - The Heart of Mechanical Keyboard Pro
 * 
 * This is the actual Android IME service that integrates with the system.
 * It creates the keyboard view and handles all input connections.
 * 
 * Lifecycle:
 * 1. onCreate() - Initialize engines
 * 2. onCreateInputView() - Build the keyboard UI
 * 3. onStartInput() - New input field focused
 * 4. onKeyDown/Up - Hardware key events
 * 5. onFinishInput() - Input field lost focus
 * 6. onDestroy() - Cleanup
 */
@AndroidEntryPoint
class MKProInputMethodService : InputMethodService() {

    // ═══════════════════════════════════════════════════════
    // Injected Engines (via Hilt DI)
    // ═══════════════════════════════════════════════════════
    @Inject lateinit var keyboardEngine: KeyboardEngine
    @Inject lateinit var layoutEngine: LayoutEngine
    @Inject lateinit var themeEngine: ThemeEngine
    @Inject lateinit var soundEngine: SoundEngine
    @Inject lateinit var hapticEngine: HapticEngine
    @Inject lateinit var clipboardManager: ClipboardManager

    // UI Components
    private var inputContainer: InputContainer? = null

    // ═══════════════════════════════════════════════════════
    // Service Lifecycle
    // ═══════════════════════════════════════════════════════
    override fun onCreate() {
        super.onCreate()
        // Engines are injected automatically by Hilt
    }

    /**
     * Called by the system when it needs the input view.
     * This is where we create our custom mechanical keyboard.
     */
    override fun onCreateInputView(): View {
        inputContainer = InputContainer(this).apply {
            setKeyboardEngine(keyboardEngine)
            setLayoutEngine(layoutEngine)
            setThemeEngine(themeEngine)
        }
        return inputContainer!!
    }

    /**
     * Called when a new input field gains focus.
     * We use this to detect input type and adjust the keyboard.
     */
    override fun onStartInput(attribute: EditorInfo, restarting: Boolean) {
        super.onStartInput(attribute, restarting)

        // Set the input connection for the keyboard engine
        keyboardEngine.setInputConnection(currentInputConnection)

        // Detect input type and potentially adjust layout
        when (attribute.inputType and EditorInfo.TYPE_MASK_CLASS) {
            EditorInfo.TYPE_CLASS_NUMBER,
            EditorInfo.TYPE_CLASS_PHONE -> {
                // Could switch to numeric layout in future phases
            }
            EditorInfo.TYPE_CLASS_TEXT -> {
                // Standard text input - QWERTY layout
            }
        }

        // Reset modifier states when switching input fields
        keyboardEngine.releaseAllModifiers()
    }

    /**
     * Called when the input field loses focus.
     */
    override fun onFinishInput() {
        super.onFinishInput()
        keyboardEngine.resetState()
        keyboardEngine.setInputConnection(null)
    }

    /**
     * Handle hardware keyboard key down events.
     * Useful for handling external keyboards or hardware buttons.
     */
    override fun onKeyDown(keyCode: Int, event: android.view.KeyEvent?): Boolean {
        // Let the system handle hardware keyboard events
        // Our virtual keyboard handles touch events separately
        return super.onKeyDown(keyCode, event)
    }

    /**
     * Called when the input view is being destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()
        soundEngine.release()
        inputContainer = null
    }
}
