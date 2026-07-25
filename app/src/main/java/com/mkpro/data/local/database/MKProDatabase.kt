package com.mkpro.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkpro.data.local.database.dao.*
import com.mkpro.data.local.database.entity.*

@Database(
    entities = [
        LayoutEntity::class,
        ThemeEntity::class,
        SettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MKProDatabase : RoomDatabase() {
    abstract fun layoutDao(): LayoutDao
    abstract fun themeDao(): ThemeDao
    abstract fun settingsDao(): SettingsDao
}
