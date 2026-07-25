package com.mkpro.data.local.database.dao

import androidx.room.*
import com.mkpro.data.local.database.entity.ThemeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThemeDao {
    @Query("SELECT * FROM themes")
    fun getAll(): Flow<List<ThemeEntity>>

    @Query("SELECT * FROM themes WHERE id = :id")
    suspend fun getById(id: String): ThemeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(theme: ThemeEntity)

    @Delete
    suspend fun delete(theme: ThemeEntity)
}
