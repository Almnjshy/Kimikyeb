# Mechanical Keyboard Pro - Phase 1

## 🎯 Overview

**Mechanical Keyboard Pro** is a premium Android Input Method Editor (IME) that brings the complete desktop keyboard experience to mobile devices.

### Phase 1 Features
- ✅ Full Android IME integration (works in all apps)
- ✅ Premium mechanical keyboard design with 3D keys and shadows
- ✅ QWERTY layout with full character support
- ✅ Modifier keys (Shift, Ctrl, Alt, Fn)
- ✅ Action keys (Enter, Backspace, Tab, Escape, Space)
- ✅ Navigation keys (Arrow keys)
- ✅ Function keys (F1-F12)
- ✅ Desktop shortcuts (Ctrl+C, Ctrl+V, etc.)
- ✅ Shift lock (double-tap Shift)
- ✅ Press animations with ripple effects
- ✅ Haptic feedback
- ✅ Sound engine (mechanical switch sounds)
- ✅ Multiple themes (Dark Mechanical, Carbon)
- ✅ Responsive layout engine
- ✅ Hardware acceleration
- ✅ Clean Architecture with Hilt DI

## 🏗 Architecture

```
app/
├── app/                    # Application & DI
├── domain/model/           # Data models
├── engine/                 # Core engines
│   ├── KeyboardEngine      # Key event processing
│   ├── LayoutEngine        # Position calculation
│   ├── ThemeEngine         # Visual theming
│   ├── SoundEngine         # Audio feedback
│   ├── HapticEngine        # Vibration feedback
│   └── ClipboardManager    # Clipboard operations
├── service/                # IME Service
│   ├── MKProInputMethodService
│   └── inputview/
│       ├── InputContainer
│       └── MechanicalKeyboardView  # Custom rendering
├── data/                   # Data layer
│   └── local/database/
│       ├── MKProDatabase
│       ├── dao/
│       └── entity/
└── presentation/             # UI layer
    └── settings/
        └── SettingsActivity
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- JDK 17
- Android SDK 34
- Kotlin 1.9.21

### Build Instructions

```bash
# Clone the repository
git clone <repository-url>
cd mechanical-keyboard-pro

# Build debug APK
./gradlew assembleDebug

# Or build release
./gradlew assembleRelease

# Run tests
./gradlew test
```

### Installation

1. Build the APK using the instructions above
2. Install on your Android device:
   ```bash
   adb install app/build/outputs/apk/debug/app-debug.apk
   ```
3. Open the app and follow setup:
   - Tap "Enable Keyboard" → Enable "MKPro Keyboard"
   - Tap "Select MKPro" → Choose MKPro from the picker
4. Open any text field and enjoy the mechanical keyboard!

## 🎨 Themes

### Dark Mechanical (Default)
- Deep dark background (#1A1A1E)
- Purple accent (#6C63FF)
- 3D key shadows
- Smooth press animations

### Carbon
- Pure black background
- Orange accent (#FF6B35)
- Minimalist design
- Sharp corners

## 🎹 Key Features

### Modifier Keys
- **Shift**: Single press for temporary shift, double-tap for Caps Lock
- **Ctrl**: Hold for shortcuts (Ctrl+C, Ctrl+V, Ctrl+A, Ctrl+Z, Ctrl+Y)
- **Alt**: Hold for alternate functions
- **Fn**: Function layer access

### Special Keys
- **Esc**: Sends escape key event
- **Tab**: Sends tab key event
- **Enter**: Sends enter key event
- **Backspace**: Deletes character before cursor
- **Arrows**: Navigation in text

### Animations
- Key press scale animation (96% scale)
- Ripple effect on touch
- Glow effect on press
- Smooth shadow transitions

## 📁 Project Structure

```
mechanical-keyboard-pro/
├── .github/
│   └── workflows/
│       └── ci.yml              # GitHub Actions CI
├── app/
│   ├── build.gradle.kts        # App build config
│   ├── proguard-rules.pro      # ProGuard rules
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/mkpro/
│           │   ├── app/
│           │   │   ├── MKProApplication.kt
│           │   │   └── di/
│           │   │       ├── AppModule.kt
│           │   │       ├── ServiceModule.kt
│           │   │       └── EngineModule.kt
│           │   ├── domain/
│           │   │   └── model/
│           │   │       ├── KeyDefinition.kt
│           │   │       ├── KeyboardLayout.kt
│           │   │       ├── KeyboardTheme.kt
│           │   │       └── KeyPosition.kt
│           │   ├── engine/
│           │   │   ├── KeyboardEngine.kt
│           │   │   ├── LayoutEngine.kt
│           │   │   ├── ThemeEngine.kt
│           │   │   ├── SoundEngine.kt
│           │   │   ├── HapticEngine.kt
│           │   │   └── ClipboardManager.kt
│           │   ├── service/
│           │   │   ├── MKProInputMethodService.kt
│           │   │   └── inputview/
│           │   │       ├── InputContainer.kt
│           │   │       └── MechanicalKeyboardView.kt
│           │   ├── data/
│           │   │   └── local/
│           │   │       └── database/
│           │   │           ├── MKProDatabase.kt
│           │   │           ├── dao/
│           │   │           │   ├── LayoutDao.kt
│           │   │           │   ├── ThemeDao.kt
│           │   │           │   └── SettingsDao.kt
│           │   │           └── entity/
│           │   │               ├── LayoutEntity.kt
│           │   │               ├── ThemeEntity.kt
│           │   │               └── SettingsEntity.kt
│           │   └── presentation/
│           │       └── settings/
│           │           └── SettingsActivity.kt
│           └── res/
│               ├── drawable/
│               │   ├── ic_keyboard.xml
│               │   ├── key_background_default.xml
│               │   ├── key_background_pressed.xml
│               │   ├── key_background_modifier.xml
│               │   └── key_background_enter.xml
│               ├── layout/
│               │   └── activity_settings.xml
│               ├── mipmap-anydpi-v26/
│               │   ├── ic_launcher.xml
│               │   └── ic_launcher_round.xml
│               ├── values/
│               │   ├── strings.xml
│               │   ├── colors.xml
│               │   ├── themes.xml
│               │   └── dimens.xml
│               └── xml/
│                   ├── method.xml
│                   └── keyboard_input.xml
├── build.gradle.kts            # Root build config
├── settings.gradle.kts         # Project settings
├── gradle.properties           # Gradle properties
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── gradlew                     # Gradle wrapper (Unix)
├── gradlew.bat                 # Gradle wrapper (Windows)
└── README.md                   # This file
```

## 🔧 Technical Details

### Custom Rendering
- **Canvas API** for maximum performance
- **Hardware acceleration** enabled
- **Object pooling** for Paint objects
- **Pre-allocated** RectF and Path objects
- **60fps target** with 16ms frame budget

### Architecture Patterns
- **MVVM** for UI layer
- **Clean Architecture** with domain/data separation
- **Repository Pattern** for data access
- **Dependency Injection** with Hilt
- **Reactive programming** with Kotlin Flow

### Performance Optimizations
- Cached key positions (recalculated only on size change)
- Pre-allocated paint objects
- Hardware layer rendering
- Efficient touch event handling
- Minimal object allocation during draw calls

## 🗺 Roadmap

### Phase 2: Desktop Keys + Command Bar
- Full desktop key set (F13-F24, Print Screen, Scroll Lock, Pause)
- Expandable command bar
- Media controls
- Numeric keypad

### Phase 3: Layer System
- Multiple keyboard layers (Normal, Desktop, Programming, Gaming)
- Layer switching
- Custom layer creation

### Phase 4: Macro System
- Macro recording and playback
- Macro editor
- Trigger types (tap, long-press, double-tap)

### Phase 5: Customization Engine
- Theme editor
- Layout designer (drag & drop)
- Import/export themes and layouts
- Mechanical switch sound profiles

### Phase 6: PC Connectivity (Optional)
- Bluetooth HID
- USB HID
- Wi-Fi/WebSocket connection

## 📝 License

Copyright © 2026 Mechanical Keyboard Pro. All rights reserved.

## 🤝 Contributing

Contributions are welcome! Please read our contributing guidelines before submitting PRs.

## 📧 Support

For support, email support@mkpro.app or open an issue on GitHub.

---

**Built with ❤️ for keyboard enthusiasts**
