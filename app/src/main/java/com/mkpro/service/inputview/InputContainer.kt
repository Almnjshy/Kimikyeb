package com.mkpro.service.inputview

import android.content.Context
import android.widget.FrameLayout
import com.mkpro.engine.KeyboardEngine
import com.mkpro.engine.LayoutEngine
import com.mkpro.engine.ThemeEngine

/**
 * InputContainer is the root view that holds all keyboard components.
 * 
 * Structure:
 * - CommandBar (top, collapsible)
 * - MechanicalKeyboardView (main keyboard)
 * 
 * This container manages the layout and sizing of all child views.
 */
class InputContainer(context: Context) : FrameLayout(context) {

    private lateinit var keyboardEngine: KeyboardEngine
    private lateinit var layoutEngine: LayoutEngine
    private lateinit var themeEngine: ThemeEngine

    private var keyboardView: MechanicalKeyboardView? = null

    init {
        // Set background color from theme
        setBackgroundColor(0xFF1A1A1E.toInt())
    }

    fun setKeyboardEngine(engine: KeyboardEngine) {
        keyboardEngine = engine
        keyboardView?.setKeyboardEngine(engine)
    }

    fun setLayoutEngine(engine: LayoutEngine) {
        layoutEngine = engine
        keyboardView?.setLayoutEngine(engine)
    }

    fun setThemeEngine(engine: ThemeEngine) {
        themeEngine = engine
        keyboardView?.setThemeEngine(engine)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // Create keyboard view when attached to window
        if (keyboardView == null) {
            keyboardView = MechanicalKeyboardView(context).apply {
                if (::keyboardEngine.isInitialized) setKeyboardEngine(keyboardEngine)
                if (::layoutEngine.isInitialized) setLayoutEngine(layoutEngine)
                if (::themeEngine.isInitialized) setThemeEngine(themeEngine)
            }
            addView(keyboardView, LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            ))
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeAllViews()
        keyboardView = null
    }
}
