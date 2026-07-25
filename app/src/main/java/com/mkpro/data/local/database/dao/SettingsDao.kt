package com.mkpro.data.local.database.dao

import androidx.room.*
import com.mkpro.data.local.database.entity.SettingsEntity

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE `key` = :key")
    suspend fun get(key: String): SettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun set(setting: SettingsEntity)

    @Query("DELETE FROM settings WHERE `key` = :key")
    suspend fun delete(key: String)
}
