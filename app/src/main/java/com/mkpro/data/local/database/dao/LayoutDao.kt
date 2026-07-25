package com.mkpro.data.local.database.dao

import androidx.room.*
import com.mkpro.data.local.database.entity.LayoutEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LayoutDao {
    @Query("SELECT * FROM layouts")
    fun getAll(): Flow<List<LayoutEntity>>

    @Query("SELECT * FROM layouts WHERE id = :id")
    suspend fun getById(id: String): LayoutEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(layout: LayoutEntity)

    @Delete
    suspend fun delete(layout: LayoutEntity)

    @Query("DELETE FROM layouts WHERE isBuiltIn = 0")
    suspend fun deleteAllCustom()
}
