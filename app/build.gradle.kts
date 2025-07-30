plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    //alias(libs.plugins.google.gms.google.services)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.projectmate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projectmate"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation("com.kakao.sdk:v2-all:2.21.4") // kakao 전체 모듈 설치
    implementation ("com.github.bumptech.glide:glide:4.15.1") // glide 의존성 추가
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1") // "

    //implementation(libs.firebase.analytics)
    //implementation(libs.firebase.auth)
    //implementation(libs.firebase.database)
    //implementation(libs.firebase.database.ktx)

    // Import the Firebase BoM
   // implementation(platform("com.google.firebase:firebase-bom:34.0.0"))
  //  implementation("com.google.firebase:firebase-analytics")
   // implementation("com.google.firebase:firebase-auth-ktx")
   // implementation("com.google.firebase:firebase-database-ktx")
  //  implementation("com.google.firebase:firebase-firestore-ktx")

  //  implementation(libs.firebase.database)
 //   implementation(libs.firebase.auth)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


}