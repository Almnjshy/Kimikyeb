package com.mkpro.presentation.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mkpro.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint

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
        binding.btnEnableKeyboard.setOnClickListener {
            openKeyboardSettings()
        }
        
        binding.btnSelectKeyboard.setOnClickListener {
            showInputMethodPicker()
        }
        
        binding.switchSound.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Sound " + if (isChecked) "enabled" else "disabled", Toast.LENGTH_SHORT).show()
        }
        
        binding.switchVibration.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(this, "Vibration " + if (isChecked) "enabled" else "disabled", Toast.LENGTH_SHORT).show()
        }
        
        binding.sliderHeight.addOnChangeListener { _, value, _ ->
        }
    }
    
    private fun checkKeyboardStatus() {
        val enabledInputMethods = inputMethodManager.enabledInputMethodList
        val isEnabled = enabledInputMethods.any { 
            it.packageName == packageName 
        }
        
        val isSelected = isMkProSelected()
        
        binding.tvStatus.text = when {
            isEnabled && isSelected -> "MKPro is active and ready!"
            isEnabled && !isSelected -> "MKPro is enabled but not selected"
            else -> "MKPro is not enabled"
        }
        
        binding.btnEnableKeyboard.isEnabled = !isEnabled
        binding.btnSelectKeyboard.isEnabled = isEnabled
    }
    
    private fun isMkProSelected(): Boolean {
        val currentInputMethodId = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.DEFAULT_INPUT_METHOD
        )
        return currentInputMethodId?.contains(packageName) == true
    }
    
    private fun openKeyboardSettings() {
        val intent = Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
        startActivity(intent)
        Toast.makeText(this, "Enable MKPro Keyboard in the list", Toast.LENGTH_LONG).show()
    }
    
    private fun showInputMethodPicker() {
        inputMethodManager.showInputMethodPicker()
    }
}
