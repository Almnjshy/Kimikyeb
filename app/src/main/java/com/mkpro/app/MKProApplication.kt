package com.mkpro.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Mechanical Keyboard Pro - Application Entry Point
 * 
 * Initializes dependency injection and global application state.
 * This is the foundation of the entire app architecture.
 */
@HiltAndroidApp
class MKProApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Global initialization happens here
        // - Timber logging (in production)
        // - Crash reporting setup
        // - Analytics initialization
    }
}
