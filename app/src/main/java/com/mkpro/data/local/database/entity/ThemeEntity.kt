package com.mkpro.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "themes")
data class ThemeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val isBuiltIn: Boolean,
    val themeJson: String,
    val createdAt: Long = System.currentTimeMillis()
)
