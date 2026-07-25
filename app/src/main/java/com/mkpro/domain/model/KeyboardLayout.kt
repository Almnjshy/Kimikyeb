package com.mkpro.domain.model

/**
 * Represents a complete keyboard layout with rows of keys.
 * Supports dynamic layout calculation and customization.
 */
data class KeyboardLayout(
    val id: String,
    val name: String,
    val description: String,
    val rows: List<KeyboardRow>,
    val isBuiltIn: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    companion object {
        /**
         * Default QWERTY layout for Phase 1.
         * This is the primary layout users see on first launch.
         */
        val QWERTY_DEFAULT = KeyboardLayout(
            id = "qwerty_default",
            name = "QWERTY",
            description = "Standard QWERTY layout with mechanical design",
            rows = listOf(
                // Row 1: Numbers and symbols
                KeyboardRow(
                    keys = listOf(
                        KeyDefinition.GRAVE,
                        KeyDefinition.NUM1,
                        KeyDefinition.NUM2,
                        KeyDefinition.NUM3,
                        KeyDefinition.NUM4,
                        KeyDefinition.NUM5,
                        KeyDefinition.NUM6,
                        KeyDefinition.NUM7,
                        KeyDefinition.NUM8,
                        KeyDefinition.NUM9,
                        KeyDefinition.NUM0,
                        KeyDefinition.MINUS,
                        KeyDefinition.EQUALS,
                        KeyDefinition.BACKSPACE
                    )
                ),
                // Row 2: QWERTY
                KeyboardRow(
                    keys = listOf(
                        KeyDefinition.TAB,
                        KeyDefinition.Q,
                        KeyDefinition.W,
                        KeyDefinition.E,
                        KeyDefinition.R,
                        KeyDefinition.T,
                        KeyDefinition.Y,
                        KeyDefinition.U,
                        KeyDefinition.I,
                        KeyDefinition.O,
                        KeyDefinition.P,
                        KeyDefinition.LBRACKET,
                        KeyDefinition.RBRACKET,
                        KeyDefinition.BACKSLASH
                    )
                ),
                // Row 3: ASDF
                KeyboardRow(
                    keys = listOf(
                        KeyDefinition.ESC,
                        KeyDefinition.A,
                        KeyDefinition.S,
                        KeyDefinition.D,
                        KeyDefinition.F,
                        KeyDefinition.G,
                        KeyDefinition.H,
                        KeyDefinition.J,
                        KeyDefinition.K,
                        KeyDefinition.L,
                        KeyDefinition.SEMICOLON,
                        KeyDefinition.APOSTROPHE,
                        KeyDefinition.ENTER
                    )
                ),
                // Row 4: ZXCV
                KeyboardRow(
                    keys = listOf(
                        KeyDefinition.SHIFT,
                        KeyDefinition.Z,
                        KeyDefinition.X,
                        KeyDefinition.C,
                        KeyDefinition.V,
                        KeyDefinition.B,
                        KeyDefinition.N,
                        KeyDefinition.M,
                        KeyDefinition.COMMA,
                        KeyDefinition.PERIOD,
                        KeyDefinition.SLASH,
                        KeyDefinition.SHIFT_RIGHT
                    )
                ),
                // Row 5: Bottom row
                KeyboardRow(
                    keys = listOf(
                        KeyDefinition.CTRL,
                        KeyDefinition.ALT,
                        KeyDefinition.FN,
                        KeyDefinition.SYMBOLS,
                        KeyDefinition.SPACE,
                        KeyDefinition.COMMA_KEY,
                        KeyDefinition.PERIOD_KEY,
                        KeyDefinition.EMOJI,
                        KeyDefinition.ENTER
                    )
                )
            )
        )

        /**
         * Compact layout for smaller screens (phones in portrait).
         */
        val QWERTY_COMPACT = KeyboardLayout(
            id = "qwerty_compact",
            name = "QWERTY Compact",
            description = "Compact QWERTY for smaller screens",
            rows = listOf(
                KeyboardRow(
                    keys = listOf(
                        KeyDefinition.Q, KeyDefinition.W, KeyDefinition.E, KeyDefinition.R,
                        KeyDefinition.T, KeyDefinition.Y, KeyDefinition.U, KeyDefinition.I,
                        KeyDefinition.O, KeyDefinition.P
                    )
                ),
                KeyboardRow(
                    keys = listOf(
                        KeyDefinition.A, KeyDefinition.S, KeyDefinition.D, KeyDefinition.F,
                        KeyDefinition.G, KeyDefinition.H, KeyDefinition.J, KeyDefinition.K,
                        KeyDefinition.L
                    )
                ),
                KeyboardRow(
                    keys = listOf(
                        KeyDefinition.SHIFT, KeyDefinition.Z, KeyDefinition.X, KeyDefinition.C,
                        KeyDefinition.V, KeyDefinition.B, KeyDefinition.N, KeyDefinition.M,
                        KeyDefinition.BACKSPACE
                    )
                ),
                KeyboardRow(
                    keys = listOf(
                        KeyDefinition.SYMBOLS, KeyDefinition.COMMA_KEY, KeyDefinition.SPACE,
                        KeyDefinition.PERIOD_KEY, KeyDefinition.ENTER
                    )
                )
            )
        )
    }
}

data class KeyboardRow(
    val keys: List<KeyDefinition>
)
