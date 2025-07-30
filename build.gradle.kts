<<<<<<< HEAD
// 루트 build.gradle.kts
plugins {
    id("com.google.gms.google-services") version "4.4.1" apply false // ✅ Firebase용
    id("com.android.application") version "8.4.1" apply false        // ✅ Android 앱용
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false   // ✅ Kotlin Android
}
=======
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    //alias(libs.plugins.google.gms.google.services) apply false
    id("com.google.gms.google-services") version "4.4.3" apply false
}
>>>>>>> f9c06176761ab9c7bc48d0aa008b8fbddb73ea64
