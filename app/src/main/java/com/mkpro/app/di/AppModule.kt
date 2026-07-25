package com.mkpro.app.di

import android.content.Context
import androidx.room.Room
import com.mkpro.data.local.database.MKProDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Application-level dependency injection module.
 * Provides singleton instances of core components.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MKProDatabase {
        return Room.databaseBuilder(
            context,
            MKProDatabase::class.java,
            "mkpro_database"
        ).build()
    }
}
