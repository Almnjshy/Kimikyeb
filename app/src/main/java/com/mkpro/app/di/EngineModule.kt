package com.mkpro.app.di

import android.content.Context
import com.mkpro.engine.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object EngineModule {

    @Provides
    @ServiceScoped
    fun provideKeyboardEngine(
        layoutEngine: LayoutEngine,
        soundEngine: SoundEngine,
        hapticEngine: HapticEngine
    ): KeyboardEngine {
        return KeyboardEngine(layoutEngine, soundEngine, hapticEngine)
    }

    @Provides
    @ServiceScoped
    fun provideLayoutEngine(): LayoutEngine {
        return LayoutEngine()
    }

    @Provides
    @ServiceScoped
    fun provideThemeEngine(): ThemeEngine {
        return ThemeEngine()
    }

    @Provides
    @ServiceScoped
    fun provideSoundEngine(@ApplicationContext context: Context): SoundEngine {
        return SoundEngine(context)
    }

    @Provides
    @ServiceScoped
    fun provideHapticEngine(@ApplicationContext context: Context): HapticEngine {
        return HapticEngine(context)
    }

    @Provides
    @ServiceScoped
    fun provideClipboardManager(@ApplicationContext context: Context): ClipboardManager {
        return ClipboardManager(context)
    }
}
