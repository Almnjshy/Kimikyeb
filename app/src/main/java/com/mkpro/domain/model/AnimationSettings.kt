package com.mkpro.domain.model

data class AnimationSettings(
    val pressDurationMs: Long = 80L,
    val rippleDurationMs: Long = 300L,
    val glowEnabled: Boolean = true,
    val shadowAnimationEnabled: Boolean = true
)