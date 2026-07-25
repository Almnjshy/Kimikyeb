package com.mkpro.presentation.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mkpro.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * SettingsActivity - Main entry point for the app.
 * 
 * Provides:
 * - Enable keyboard button (opens system settings)
 * - Select keyboard button (switches to MKPro)
 * - Basic appearance settings
 * - About section
 */
@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        setupUI()
        checkKeyboardStatus()
    }

    override fun onResume() {
        super.onResume()
        checkKeyboardStatus()
    }

    private fun setupUI() {
        // Enable Keyboard Button
        binding.btnEnableKeyboard.setOnClickListener {
            openKeyboardSettings()
        }

        // Select Keyboard Button
        binding.btnSelectKeyboard.setOnClickListener {
            showInputMethodPicker()
        }

        // Sound toggle
        binding.switchSound.setOnCheckedChangeListener { _, isChecked ->
            // Save preference - Phase 1 basic
            Toast.makeText(this, "Sound ${if (isChecked) "enabled" else "disabled"}", Toast.LENGTH_SHORT).show()
        }

        // Vibration toggle
        binding.switchVibration.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Vibration ${if (isChecked) "enabled" else "disabled"}", Toast.LENGTH_SHORT).show()
        }

        // Keyboard height slider
        binding.sliderHeight.addOnChangeListener { _, value, _ ->
            // Save height preference
        }
    }

    private fun checkKeyboardStatus() {
        val enabledInputMethods = inputMethodManager.enabledInputMethodList
        val isEnabled = enabledInputMethods.any { 
            it.packageName == packageName 
        }

        val isSelected = isMkProSelected()

        // Update UI based on status
        binding.tvStatus.text = when {
            isEnabled && isSelected -> "✅ MKPro is active and ready!"
            isEnabled && !isSelected -> "⚠️ MKPro is enabled but not selected"
            else -> "❌ MKPro is not enabled"
        }

        binding.btnEnableKeyboard.isEnabled = !isEnabled
        binding.btnSelectKeyboard.isEnabled = isEnabled
    }

    private fun isMkProSelected(): Boolean {
        // Check if MKPro is the current default keyboard
        val currentInputMethodId = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.DEFAULT_INPUT_METHOD
        )
        return currentInputMethodId?.contains(packageName) == true
    }

    private fun openKeyboardSettings() {
        val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
        startActivity(intent)
        Toast.makeText(this, "Enable "MKPro Keyboard" in the list", Toast.LENGTH_LONG).show()
    }

    private fun showInputMethodPicker() {
        inputMethodManager.showInputMethodPicker()
    }
}
