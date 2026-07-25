package com.mkpro.engine

import com.mkpro.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * LayoutEngine calculates key positions and manages layout switching.
 * 
 * Responsibilities:
 * - Calculate exact pixel positions for each key
 * - Handle different screen sizes and orientations
 * - Switch between layouts (QWERTY, Symbols, etc.)
 * - Support compact mode for smaller screens
 */
@Singleton
class LayoutEngine @Inject constructor() {

    private val _currentLayout = MutableStateFlow(KeyboardLayout.QWERTY_DEFAULT)
    val currentLayout: StateFlow<KeyboardLayout> = _currentLayout.asStateFlow()

    private val _isSymbolsMode = MutableStateFlow(false)
    val isSymbolsMode: StateFlow<Boolean> = _isSymbolsMode.asStateFlow()

    // Cache for calculated positions
    private var cachedPositions: List<KeyPosition> = emptyList()
    private var cachedWidth: Int = 0
    private var cachedHeight: Int = 0

    // ═══════════════════════════════════════════════════════
    // Layout Switching
    // ═══════════════════════════════════════════════════════
    fun switchToLayout(layout: KeyboardLayout) {
        _currentLayout.value = layout
        _isSymbolsMode.value = false
        invalidateCache()
    }

    fun switchToSymbolsLayout() {
        _isSymbolsMode.value = true
        // In Phase 1, we keep the same layout but shift labels are shown
        // Phase 2+ will have a dedicated symbols layout
        invalidateCache()
    }

    fun switchToLettersLayout() {
        _isSymbolsMode.value = false
        invalidateCache()
    }

    // ═══════════════════════════════════════════════════════
    // Position Calculation
    // ═══════════════════════════════════════════════════════
    fun calculatePositions(containerWidth: Int, containerHeight: Int): List<KeyPosition> {
        // Return cached positions if dimensions haven't changed
        if (containerWidth == cachedWidth && containerHeight == cachedHeight && cachedPositions.isNotEmpty()) {
            return cachedPositions
        }

        val layout = _currentLayout.value
        val positions = mutableListOf<KeyPosition>()

        val padding = 4f  // Keyboard padding in pixels
        val availableWidth = containerWidth - (padding * 2)
        val availableHeight = containerHeight - (padding * 2)

        val rowHeight = availableHeight / layout.rows.size

        layout.rows.forEachIndexed { rowIndex, row ->
            val totalWeight = row.keys.sumOf { it.weight.toDouble() }.toFloat()
            var currentX = padding

            row.keys.forEach { key ->
                val keyWidth = (availableWidth * key.weight / totalWeight)
                val keyHeight = rowHeight - 6f  // Row spacing

                positions.add(KeyPosition(
                    key = key,
                    x = currentX,
                    y = padding + (rowIndex * rowHeight),
                    width = keyWidth - 3f,  // Key spacing
                    height = keyHeight
                ))

                currentX += keyWidth
            }
        }

        // Cache results
        cachedPositions = positions
        cachedWidth = containerWidth
        cachedHeight = containerHeight

        return positions
    }

    // ═══════════════════════════════════════════════════════
    // Key Lookup
    // ═══════════════════════════════════════════════════════
    fun findKeyAt(touchX: Float, touchY: Float, positions: List<KeyPosition>): KeyDefinition? {
        return positions.find { it.contains(touchX, touchY) }?.key
    }

    fun findKeyById(keyId: String, positions: List<KeyPosition>): KeyPosition? {
        return positions.find { it.key.id == keyId }
    }

    // ═══════════════════════════════════════════════════════
    // Cache Management
    // ═══════════════════════════════════════════════════════
    private fun invalidateCache() {
        cachedPositions = emptyList()
        cachedWidth = 0
        cachedHeight = 0
    }

    fun onConfigurationChanged() {
        invalidateCache()
    }

    // ═══════════════════════════════════════════════════════
    // Layout Selection
    // ═══════════════════════════════════════════════════════
    fun selectLayoutForScreen(widthDp: Int, heightDp: Int): KeyboardLayout {
        return when {
            widthDp < 360 -> KeyboardLayout.QWERTY_COMPACT
            else -> KeyboardLayout.QWERTY_DEFAULT
        }
    }
}
