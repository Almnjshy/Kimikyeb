package com.mkpro.domain.model

import androidx.annotation.DrawableRes

/**
 * Represents a single key on the mechanical keyboard.
 * This is the core data structure for all keyboard layouts.
 */
data class KeyDefinition(
    val id: String,
    val label: String,
    val type: KeyType,
    val character: Char? = null,
    val keyCode: Int? = null,
    val weight: Float = 1.0f,
    @DrawableRes val iconRes: Int? = null,
    val shiftLabel: String? = null,
    val shiftCharacter: Char? = null,
    val isRepeatable: Boolean = false,
    val longPressAction: KeyAction? = null
) {
    companion object {
        // Row 1: QWERTY
        val Q = KeyDefinition("q", "q", KeyType.CHARACTER, 'q', weight = 1.0f, shiftLabel = "Q", shiftCharacter = 'Q')
        val W = KeyDefinition("w", "w", KeyType.CHARACTER, 'w', weight = 1.0f, shiftLabel = "W", shiftCharacter = 'W')
        val E = KeyDefinition("e", "e", KeyType.CHARACTER, 'e', weight = 1.0f, shiftLabel = "E", shiftCharacter = 'E')
        val R = KeyDefinition("r", "r", KeyType.CHARACTER, 'r', weight = 1.0f, shiftLabel = "R", shiftCharacter = 'R')
        val T = KeyDefinition("t", "t", KeyType.CHARACTER, 't', weight = 1.0f, shiftLabel = "T", shiftCharacter = 'T')
        val Y = KeyDefinition("y", "y", KeyType.CHARACTER, 'y', weight = 1.0f, shiftLabel = "Y", shiftCharacter = 'Y')
        val U = KeyDefinition("u", "u", KeyType.CHARACTER, 'u', weight = 1.0f, shiftLabel = "U", shiftCharacter = 'U')
        val I = KeyDefinition("i", "i", KeyType.CHARACTER, 'i', weight = 1.0f, shiftLabel = "I", shiftCharacter = 'I')
        val O = KeyDefinition("o", "o", KeyType.CHARACTER, 'o', weight = 1.0f, shiftLabel = "O", shiftCharacter = 'O')
        val P = KeyDefinition("p", "p", KeyType.CHARACTER, 'p', weight = 1.0f, shiftLabel = "P", shiftCharacter = 'P')

        // Row 2: ASDF
        val A = KeyDefinition("a", "a", KeyType.CHARACTER, 'a', weight = 1.0f, shiftLabel = "A", shiftCharacter = 'A')
        val S = KeyDefinition("s", "s", KeyType.CHARACTER, 's', weight = 1.0f, shiftLabel = "S", shiftCharacter = 'S')
        val D = KeyDefinition("d", "d", KeyType.CHARACTER, 'd', weight = 1.0f, shiftLabel = "D", shiftCharacter = 'D')
        val F = KeyDefinition("f", "f", KeyType.CHARACTER, 'f', weight = 1.0f, shiftLabel = "F", shiftCharacter = 'F')
        val G = KeyDefinition("g", "g", KeyType.CHARACTER, 'g', weight = 1.0f, shiftLabel = "G", shiftCharacter = 'G')
        val H = KeyDefinition("h", "h", KeyType.CHARACTER, 'h', weight = 1.0f, shiftLabel = "H", shiftCharacter = 'H')
        val J = KeyDefinition("j", "j", KeyType.CHARACTER, 'j', weight = 1.0f, shiftLabel = "J", shiftCharacter = 'J')
        val K = KeyDefinition("k", "k", KeyType.CHARACTER, 'k', weight = 1.0f, shiftLabel = "K", shiftCharacter = 'K')
        val L = KeyDefinition("l", "l", KeyType.CHARACTER, 'l', weight = 1.0f, shiftLabel = "L", shiftCharacter = 'L')

        // Row 3: ZXCV
        val Z = KeyDefinition("z", "z", KeyType.CHARACTER, 'z', weight = 1.0f, shiftLabel = "Z", shiftCharacter = 'Z')
        val X = KeyDefinition("x", "x", KeyType.CHARACTER, 'x', weight = 1.0f, shiftLabel = "X", shiftCharacter = 'X')
        val C = KeyDefinition("c", "c", KeyType.CHARACTER, 'c', weight = 1.0f, shiftLabel = "C", shiftCharacter = 'C')
        val V = KeyDefinition("v", "v", KeyType.CHARACTER, 'v', weight = 1.0f, shiftLabel = "V", shiftCharacter = 'V')
        val B = KeyDefinition("b", "b", KeyType.CHARACTER, 'b', weight = 1.0f, shiftLabel = "B", shiftCharacter = 'B')
        val N = KeyDefinition("n", "n", KeyType.CHARACTER, 'n', weight = 1.0f, shiftLabel = "N", shiftCharacter = 'N')
        val M = KeyDefinition("m", "m", KeyType.CHARACTER, 'm', weight = 1.0f, shiftLabel = "M", shiftCharacter = 'M')

        // Numbers
        val NUM1 = KeyDefinition("1", "1", KeyType.CHARACTER, '1', weight = 1.0f, shiftLabel = "!", shiftCharacter = '!')
        val NUM2 = KeyDefinition("2", "2", KeyType.CHARACTER, '2', weight = 1.0f, shiftLabel = "@", shiftCharacter = '@')
        val NUM3 = KeyDefinition("3", "3", KeyType.CHARACTER, '3', weight = 1.0f, shiftLabel = "#", shiftCharacter = '#')
        val NUM4 = KeyDefinition("4", "4", KeyType.CHARACTER, '4', weight = 1.0f, shiftLabel = "$", shiftCharacter = '$')
        val NUM5 = KeyDefinition("5", "5", KeyType.CHARACTER, '5', weight = 1.0f, shiftLabel = "%", shiftCharacter = '%')
        val NUM6 = KeyDefinition("6", "6", KeyType.CHARACTER, '6', weight = 1.0f, shiftLabel = "^", shiftCharacter = '^')
        val NUM7 = KeyDefinition("7", "7", KeyType.CHARACTER, '7', weight = 1.0f, shiftLabel = "&", shiftCharacter = '&')
        val NUM8 = KeyDefinition("8", "8", KeyType.CHARACTER, '8', weight = 1.0f, shiftLabel = "*", shiftCharacter = '*')
        val NUM9 = KeyDefinition("9", "9", KeyType.CHARACTER, '9', weight = 1.0f, shiftLabel = "(", shiftCharacter = '(')
        val NUM0 = KeyDefinition("0", "0", KeyType.CHARACTER, '0', weight = 1.0f, shiftLabel = ")", shiftCharacter = ')')

        // Special Characters
        val MINUS = KeyDefinition("-", "-", KeyType.CHARACTER, '-', weight = 1.0f, shiftLabel = "_", shiftCharacter = '_')
        val EQUALS = KeyDefinition("=", "=", KeyType.CHARACTER, '=', weight = 1.0f, shiftLabel = "+", shiftCharacter = '+')
        val LBRACKET = KeyDefinition("[", "[", KeyType.CHARACTER, '[', weight = 1.0f, shiftLabel = "{", shiftCharacter = '{')
        val RBRACKET = KeyDefinition("]", "]", KeyType.CHARACTER, ']', weight = 1.0f, shiftLabel = "}", shiftCharacter = '}')
        val BACKSLASH = KeyDefinition("\\", "\\", KeyType.CHARACTER, '\\', weight = 1.5f, shiftLabel = "|", shiftCharacter = '|')
        val SEMICOLON = KeyDefinition(";", ";", KeyType.CHARACTER, ';', weight = 1.0f, shiftLabel = ":", shiftCharacter = ':')
        val APOSTROPHE = KeyDefinition("'", "'", KeyType.CHARACTER = ''', weight = 1.0f, shiftLabel = """, shiftCharacter = '"')
        val COMMA = KeyDefinition(",", ",", KeyType.CHARACTER, ',', weight = 1.0f, shiftLabel = "<", shiftCharacter = '<')
        val PERIOD = KeyDefinition(".", ".", KeyType.CHARACTER, '.', weight = 1.0f, shiftLabel = ">", shiftCharacter = '>')
        val SLASH = KeyDefinition("/", "/", KeyType.CHARACTER, '/', weight = 1.0f, shiftLabel = "?", shiftCharacter = '?')
        val GRAVE = KeyDefinition("`", "`", KeyType.CHARACTER, '`', weight = 1.0f, shiftLabel = "~", shiftCharacter = '~')

        // Modifier Keys
        val SHIFT = KeyDefinition("shift", "⇧", KeyType.MODIFIER, keyCode = android.view.KeyEvent.KEYCODE_SHIFT_LEFT, weight = 1.5f)
        val SHIFT_RIGHT = KeyDefinition("shift_right", "⇧", KeyType.MODIFIER, keyCode = android.view.KeyEvent.KEYCODE_SHIFT_RIGHT, weight = 1.5f)
        val CTRL = KeyDefinition("ctrl", "Ctrl", KeyType.MODIFIER, keyCode = android.view.KeyEvent.KEYCODE_CTRL_LEFT, weight = 1.2f)
        val ALT = KeyDefinition("alt", "Alt", KeyType.MODIFIER, keyCode = android.view.KeyEvent.KEYCODE_ALT_LEFT, weight = 1.2f)
        val FN = KeyDefinition("fn", "Fn", KeyType.MODIFIER, weight = 1.0f)

        // Action Keys
        val BACKSPACE = KeyDefinition("backspace", "⌫", KeyType.ACTION, keyCode = android.view.KeyEvent.KEYCODE_DEL, weight = 1.5f, isRepeatable = true)
        val ENTER = KeyDefinition("enter", "↵", KeyType.ACTION, keyCode = android.view.KeyEvent.KEYCODE_ENTER, weight = 1.5f)
        val TAB = KeyDefinition("tab", "Tab", KeyType.ACTION, keyCode = android.view.KeyEvent.KEYCODE_TAB, weight = 1.2f)
        val SPACE = KeyDefinition("space", "", KeyType.ACTION, keyCode = android.view.KeyEvent.KEYCODE_SPACE, weight = 4.0f)
        val ESC = KeyDefinition("esc", "Esc", KeyType.ACTION, keyCode = android.view.KeyEvent.KEYCODE_ESCAPE, weight = 1.0f)

        // Navigation
        val ARROW_UP = KeyDefinition("arrow_up", "↑", KeyType.NAVIGATION, keyCode = android.view.KeyEvent.KEYCODE_DPAD_UP, weight = 1.0f)
        val ARROW_DOWN = KeyDefinition("arrow_down", "↓", KeyType.NAVIGATION, keyCode = android.view.KeyEvent.KEYCODE_DPAD_DOWN, weight = 1.0f)
        val ARROW_LEFT = KeyDefinition("arrow_left", "←", KeyType.NAVIGATION, keyCode = android.view.KeyEvent.KEYCODE_DPAD_LEFT, weight = 1.0f)
        val ARROW_RIGHT = KeyDefinition("arrow_right", "→", KeyType.NAVIGATION, keyCode = android.view.KeyEvent.KEYCODE_DPAD_RIGHT, weight = 1.0f)

        // Function Keys
        val F1 = KeyDefinition("f1", "F1", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F1, weight = 1.0f)
        val F2 = KeyDefinition("f2", "F2", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F2, weight = 1.0f)
        val F3 = KeyDefinition("f3", "F3", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F3, weight = 1.0f)
        val F4 = KeyDefinition("f4", "F4", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F4, weight = 1.0f)
        val F5 = KeyDefinition("f5", "F5", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F5, weight = 1.0f)
        val F6 = KeyDefinition("f6", "F6", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F6, weight = 1.0f)
        val F7 = KeyDefinition("f7", "F7", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F7, weight = 1.0f)
        val F8 = KeyDefinition("f8", "F8", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F8, weight = 1.0f)
        val F9 = KeyDefinition("f9", "F9", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F9, weight = 1.0f)
        val F10 = KeyDefinition("f10", "F10", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F10, weight = 1.0f)
        val F11 = KeyDefinition("f11", "F11", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F11, weight = 1.0f)
        val F12 = KeyDefinition("f12", "F12", KeyType.FUNCTION, keyCode = android.view.KeyEvent.KEYCODE_F12, weight = 1.0f)

        // Special
        val SYMBOLS = KeyDefinition("symbols", "?123", KeyType.SPECIAL, weight = 1.2f)
        val LETTERS = KeyDefinition("letters", "ABC", KeyType.SPECIAL, weight = 1.2f)
        val EMOJI = KeyDefinition("emoji", "☺", KeyType.SPECIAL, weight = 1.0f)
        val LANGUAGE = KeyDefinition("language", "🌐", KeyType.SPECIAL, weight = 1.0f)
        val COMMA_KEY = KeyDefinition("comma_key", ",", KeyType.CHARACTER, ',', weight = 1.0f)
        val PERIOD_KEY = KeyDefinition("period_key", ".", KeyType.CHARACTER, '.', weight = 1.0f)
        val SETTINGS = KeyDefinition("settings", "⚙", KeyType.SPECIAL, weight = 1.0f)
    }
}

enum class KeyType {
    CHARACTER,      // Regular letter/number character
    MODIFIER,       // Shift, Ctrl, Alt, Fn
    ACTION,         // Enter, Backspace, Space, Tab, Esc
    NAVIGATION,     // Arrow keys, Home, End
    FUNCTION,       // F1-F24
    SPECIAL,        // Symbols toggle, Emoji, Language
    MACRO,          // Programmable macro key
    LAYER_SWITCH    // Switch keyboard layer
}

enum class KeyAction {
    NONE,
    SHOW_SYMBOLS,
    SHOW_EMOJI,
    SWITCH_LANGUAGE,
    OPEN_SETTINGS,
    LONG_PRESS_SYMBOLS
}
