# 𝐉𝟐𝐌𝐄 • 𝐑𝐔𝐍𝐍𝐄𝐑

Run classic J2ME (Java ME) games on modern Android devices.

## Features

- Load and play `.jar` J2ME games
- Virtual keypad with D-pad and action buttons
- Screen scaling options
- Game library management
- Modern Android UI with Material Design 3

## Building

### Local Build
```bash
./gradlew assembleDebug
```

### GitHub Actions CI/CD
The project is configured with GitHub Actions for automated builds and releases.

#### Setup for Release Builds
1. Generate a signing keystore:
```bash
keytool -genkey -v -keystore j2me-runner.keystore -alias j2merunner -keyalg RSA -keysize 2048 -validity 10000
```

2. Base64 encode the keystore:
```bash
base64 -i j2me-runner.keystore | pbcopy
```

3. Add these secrets to your GitHub repository:
   - `KEYSTORE_BASE64`: Base64 encoded keystore
   - `KEYSTORE_PASSWORD`: Keystore password
   - `KEY_ALIAS`: Key alias
   - `KEY_PASSWORD`: Key password

4. Push a tag to trigger release:
```bash
git tag -a v1.0.0 -m "First release"
git push origin v1.0.0
```

## Architecture

```
app/              - Android application module
  ui/             - Jetpack Compose UI screens and components
  JarLoader.kt    - JAR file parsing and extraction
  GameRepository.kt - Game library management

j2me-engine/      - J2ME runtime engine library
  midp/           - MIDP 2.0 API implementations
    MIDlet.kt     - MIDlet lifecycle bridge
    Canvas.kt     - Game canvas with key handling
    Graphics.kt   - 2D graphics rendering bridge
    Display.kt    - Display management
    RecordStore.kt - RMS persistent storage
    Manager.kt    - Audio/media manager
  cldc/           - CLDC 1.1 utility classes
```

## License

MIT License
