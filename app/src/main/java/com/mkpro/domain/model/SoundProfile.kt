package com.mkpro.domain.model

data class SoundProfile(
    val id: String,
    val name: String,
    val resourceId: Int? = null,
    val volume: Float = 0.5f
)