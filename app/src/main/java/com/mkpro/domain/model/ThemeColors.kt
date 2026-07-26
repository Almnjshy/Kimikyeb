package com.mkpro.domain.model

import androidx.annotation.ColorInt

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