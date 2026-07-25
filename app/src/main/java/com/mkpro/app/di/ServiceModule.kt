package com.mkpro.app.di

import android.content.Context
import android.view.inputmethod.InputMethodManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

/**
 * Service-level dependency injection module.
 * Provides dependencies scoped to the IME Service lifecycle.
 */
@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @Provides
    @ServiceScoped
    fun provideInputMethodManager(
        @ApplicationContext context: Context
    ): InputMethodManager {
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }
}
