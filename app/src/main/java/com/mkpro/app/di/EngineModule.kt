package com.mkpro.app.di

import com.mkpro.engine.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.scopes.ServiceScoped

/**
 * Engine-level dependency injection module.
 * Provides all keyboard engines scoped to the IME Service.
 */
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
    fun provideSoundEngine(): SoundEngine {
        return SoundEngine()
    }

    @Provides
    @ServiceScoped
    fun provideHapticEngine(): HapticEngine {
        return HapticEngine()
    }

    @Provides
    @ServiceScoped
    fun provideClipboardManager(): ClipboardManager {
        return ClipboardManager()
    }
}
