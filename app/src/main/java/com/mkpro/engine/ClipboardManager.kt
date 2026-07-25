package com.mkpro.engine

import android.content.ClipData
import android.content.ClipboardManager as AndroidClipboardManager
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ClipboardManager handles clipboard operations for the keyboard.
 * 
 * Phase 1: Basic copy/paste support
 * Phase 5: Full clipboard history and snippets
 */
@Singleton
class ClipboardManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as AndroidClipboardManager

    private val _clipboardText = MutableStateFlow<String?>(null)
    val clipboardText: StateFlow<String?> = _clipboardText.asStateFlow()

    fun copy(text: String) {
        val clip = ClipData.newPlainText("MKPro", text)
        clipboard.setPrimaryClip(clip)
        _clipboardText.value = text
    }

    fun paste(): String? {
        val clip = clipboard.primaryClip ?: return null
        if (clip.itemCount > 0) {
            val item = clip.getItemAt(0)
            return item.text?.toString()
        }
        return null
    }

    fun hasClipboardContent(): Boolean {
        return clipboard.hasPrimaryClip()
    }

    fun clear() {
        _clipboardText.value = null
    }
}
