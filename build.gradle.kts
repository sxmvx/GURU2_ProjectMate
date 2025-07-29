// 루트 build.gradle.kts
plugins {
    id("com.google.gms.google-services") version "4.4.1" apply false // ✅ Firebase용
    id("com.android.application") version "8.4.1" apply false        // ✅ Android 앱용
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false   // ✅ Kotlin Android
}
