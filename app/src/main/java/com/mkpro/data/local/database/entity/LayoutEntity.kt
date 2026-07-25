package com.mkpro.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "layouts")
data class LayoutEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val isBuiltIn: Boolean,
    val layoutJson: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
