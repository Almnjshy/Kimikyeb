package com.mkpro.service.inputview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.res.ResourcesCompat
import com.mkpro.domain.model.*
import com.mkpro.engine.KeyboardEngine
import com.mkpro.engine.LayoutEngine
import com.mkpro.engine.ThemeEngine
import kotlinx.coroutines.*
import kotlin.math.max
import kotlin.math.min

/**
 * MechanicalKeyboardView - Premium Mechanical Keyboard Rendering
 * 
 * This is the crown jewel of Phase 1. It renders a premium mechanical
 * keyboard with:
 * - 3D key effects with shadows and depth
 * - Smooth press animations
 * - Ripple effects
 * - Premium typography
 * - High FPS rendering with hardware acceleration
 * 
 * Uses Canvas API for maximum performance and control.
 */
class MechanicalKeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // ═══════════════════════════════════════════════════════
    // Engines (injected)
    // ═══════════════════════════════════════════════════════
    private lateinit var keyboardEngine: KeyboardEngine
    private lateinit var layoutEngine: LayoutEngine
    private lateinit var themeEngine: ThemeEngine

    // ═══════════════════════════════════════════════════════
    // Paint Objects (pre-allocated for performance)
    // ═══════════════════════════════════════════════════════
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val keyBgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val keyBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
    }
    private val ripplePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val glowPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.NORMAL)
    }

    // ═══════════════════════════════════════════════════════
    // Geometry Objects (pre-allocated)
    // ═══════════════════════════════════════════════════════
    private val keyRect = RectF()
    private val shadowRect = RectF()
    private val textBounds = Rect()

    // ═══════════════════════════════════════════════════════
    // State
    // ═══════════════════════════════════════════════════════
    private var keyPositions: List<KeyPosition> = emptyList()
    private val pressedKeys = mutableSetOf<String>()
    private val pressedTimestamps = mutableMapOf<String, Long>()
    private val rippleAnimations = mutableMapOf<String, Float>()

    // Animation
    private val animatorScope = CoroutineScope(Dispatchers.Main + Job())
    private var isAnimating = false

    // ═══════════════════════════════════════════════════════
    // Performance
    // ═══════════════════════════════════════════════════════
    private var lastFrameTime = 0L
    private val targetFrameTime = 16L // 60fps

    init {
        // Enable hardware acceleration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayerType(LAYER_TYPE_HARDWARE, null)
        }
        isClickable = true
        isFocusable = true
    }

    // ═══════════════════════════════════════════════════════
    // Engine Setters
    // ═══════════════════════════════════════════════════════
    fun setKeyboardEngine(engine: KeyboardEngine) {
        keyboardEngine = engine
    }

    fun setLayoutEngine(engine: LayoutEngine) {
        layoutEngine = engine
        requestLayout()
    }

    fun setThemeEngine(engine: ThemeEngine) {
        themeEngine = engine
        invalidate()
    }

    // ═══════════════════════════════════════════════════════
    // Layout & Measurement
    // ═══════════════════════════════════════════════════════
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (::layoutEngine.isInitialized) {
            keyPositions = layoutEngine.calculatePositions(w, h)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        // Default keyboard height: 45% of screen width (standard Android IME)
        val height = (width * 0.45).toInt()
        setMeasuredDimension(width, height)
    }

    // ═══════════════════════════════════════════════════════
    // Rendering - The Heart of the Visual Experience
    // ═══════════════════════════════════════════════════════
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!::themeEngine.isInitialized || keyPositions.isEmpty()) return

        val currentTime = System.currentTimeMillis()
        lastFrameTime = currentTime

        // Draw background
        canvas.drawColor(themeEngine.getBackgroundColor())

        // Draw each key
        keyPositions.forEach { position ->
            drawKey(canvas, position, currentTime)
        }

        // Continue animation if needed
        if (rippleAnimations.isNotEmpty() || pressedKeys.isNotEmpty()) {
            invalidate()
        }
    }

    private fun drawKey(canvas: Canvas, position: KeyPosition, currentTime: Long) {
        val key = position.key
        val isPressed = pressedKeys.contains(key.id)
        val pressDuration = if (isPressed) {
            currentTime - (pressedTimestamps[key.id] ?: currentTime)
        } else 0L

        // Calculate press animation progress (0.0 to 1.0)
        val pressProgress = if (isPressed) {
            min(1f, pressDuration / 80f) // 80ms press animation
        } else {
            0f
        }

        // ═══════════════════════════════════════════════
        // 1. Draw Shadow (3D Depth Effect)
        // ═══════════════════════════════════════════════
        val elevation = themeEngine.getElevation(isPressed)
        val shadowOffset = if (isPressed) elevation * 0.5f else elevation

        shadowPaint.color = themeEngine.getShadowColor()
        shadowPaint.alpha = if (isPressed) 60 else 120

        shadowRect.set(
            position.x + shadowOffset,
            position.y + shadowOffset,
            position.x + position.width + shadowOffset,
            position.y + position.height + shadowOffset
        )

        val cornerRadius = themeEngine.getCornerRadius()
        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint)

        // ═══════════════════════════════════════════════
        // 2. Draw Key Glow (if enabled)
        // ═══════════════════════════════════════════════
        if (isPressed) {
            glowPaint.color = themeEngine.currentTheme.value.colors.accent
            glowPaint.alpha = (100 * (1 - pressProgress)).toInt()
            canvas.drawRoundRect(
                position.x - 4f, position.y - 4f,
                position.x + position.width + 4f, position.y + position.height + 4f,
                cornerRadius + 4f, cornerRadius + 4f, glowPaint
            )
        }

        // ═══════════════════════════════════════════════
        // 3. Draw Key Background
        // ═══════════════════════════════════════════════
        val bgColor = themeEngine.getKeyBackgroundColor(key.type, isPressed)
        keyBgPaint.color = bgColor

        // Apply press animation (slight scale down when pressed)
        val scaleDown = if (isPressed) 0.96f else 1.0f
        val animWidth = position.width * scaleDown
        val animHeight = position.height * scaleDown
        val animX = position.x + (position.width - animWidth) / 2f
        val animY = position.y + (position.height - animHeight) / 2f

        keyRect.set(animX, animY, animX + animWidth, animY + animHeight)
        canvas.drawRoundRect(keyRect, cornerRadius, cornerRadius, keyBgPaint)

        // ═══════════════════════════════════════════════
        // 4. Draw Key Border
        // ═══════════════════════════════════════════════
        val borderWidth = themeEngine.getBorderWidth()
        if (borderWidth > 0) {
            keyBorderPaint.color = themeEngine.getBorderColor()
            keyBorderPaint.strokeWidth = borderWidth
            canvas.drawRoundRect(keyRect, cornerRadius, cornerRadius, keyBorderPaint)
        }

        // ═══════════════════════════════════════════════
        // 5. Draw Key Label
        // ═══════════════════════════════════════════════
        val textColor = themeEngine.getKeyTextColor(isPressed)
        textPaint.color = textColor
        textPaint.textSize = themeEngine.getFontSize() * resources.displayMetrics.scaledDensity

        // Determine label (shift label if shift is active)
        val label = if (keyboardEngine.activeModifiers.value.contains(ModifierKey.SHIFT) || 
                        keyboardEngine.isShiftLocked.value) {
            key.shiftLabel ?: key.label
        } else {
            key.label
        }

        // Calculate text position (centered)
        textPaint.getTextBounds(label, 0, label.length, textBounds)
        val textX = keyRect.centerX()
        val textY = keyRect.centerY() + textBounds.height() / 2f

        canvas.drawText(label, textX, textY, textPaint)

        // ═══════════════════════════════════════════════
        // 6. Draw Ripple Effect
        // ═══════════════════════════════════════════════
        val rippleProgress = rippleAnimations[key.id]
        if (rippleProgress != null && rippleProgress < 1f) {
            ripplePaint.color = themeEngine.currentTheme.value.colors.ripple
            ripplePaint.alpha = (100 * (1 - rippleProgress)).toInt()

            val rippleRadius = max(position.width, position.height) * rippleProgress
            canvas.drawCircle(keyRect.centerX(), keyRect.centerY(), rippleRadius, ripplePaint)

            // Update ripple
            rippleAnimations[key.id] = rippleProgress + 0.08f
            if (rippleAnimations[key.id]!! >= 1f) {
                rippleAnimations.remove(key.id)
            }
        }
    }

    // ═══════════════════════════════════════════════════════
    // Touch Handling
    // ═══════════════════════════════════════════════════════
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN -> {
                val pointerIndex = event.actionIndex
                val x = event.getX(pointerIndex)
                val y = event.getY(pointerIndex)
                val pointerId = event.getPointerId(pointerIndex)

                handlePointerDown(pointerId, x, y)
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_POINTER_UP -> {
                val pointerIndex = event.actionIndex
                val pointerId = event.getPointerId(pointerIndex)

                handlePointerUp(pointerId)
            }

            MotionEvent.ACTION_MOVE -> {
                handlePointerMove(event)
            }

            MotionEvent.ACTION_CANCEL -> {
                releaseAllKeys()
            }
        }
        return true
    }

    private fun handlePointerDown(pointerId: Int, x: Float, y: Float) {
        val key = layoutEngine.findKeyAt(x, y, keyPositions) ?: return

        pressedKeys.add(key.id)
        pressedTimestamps[key.id] = System.currentTimeMillis()
        rippleAnimations[key.id] = 0f

        // Notify engine
        if (::keyboardEngine.isInitialized) {
            keyboardEngine.onKeyPress(key)
        }

        invalidate()
    }

    private fun handlePointerUp(pointerId: Int) {
        // Find which key was released (simplified - in production track pointer-key mapping)
        val keysToRelease = pressedKeys.toList()
        keysToRelease.forEach { keyId ->
            val key = keyPositions.find { it.key.id == keyId }?.key
            key?.let {
                if (::keyboardEngine.isInitialized) {
                    keyboardEngine.onKeyRelease(it)
                }
            }
            pressedKeys.remove(keyId)
            pressedTimestamps.remove(keyId)
        }

        invalidate()
    }

    private fun handlePointerMove(event: MotionEvent) {
        // Handle swipe gestures and key transitions
        // Phase 1: Basic implementation
        for (i in 0 until event.pointerCount) {
            val x = event.getX(i)
            val y = event.getY(i)

            // Check if pointer moved to a different key
            val currentKey = layoutEngine.findKeyAt(x, y, keyPositions)
            // Advanced gesture handling in Phase 2+
        }
    }

    private fun releaseAllKeys() {
        pressedKeys.clear()
        pressedTimestamps.clear()
        rippleAnimations.clear()
        if (::keyboardEngine.isInitialized) {
            keyboardEngine.resetState()
        }
        invalidate()
    }

    // ═══════════════════════════════════════════════════════
    // Cleanup
    // ═══════════════════════════════════════════════════════
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animatorScope.cancel()
        releaseAllKeys()
    }
}
