plugins {
<<<<<<< HEAD
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
<<<<<<< HEAD
    id("com.google.gms.google-services") // ✅ Firebase 플러그인
=======
    id("com.google.gms.google-services") // ✅ Firebase 플러그인 추가
>>>>>>> aa15941 (캘린더 + Firebase)
}

android {
    namespace = "com.example.test1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.test1"
=======
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
>>>>>>> f9c06176761ab9c7bc48d0aa008b8fbddb73ea64
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

<<<<<<< HEAD
    buildFeatures {
        viewBinding = true
    }

=======
>>>>>>> f9c06176761ab9c7bc48d0aa008b8fbddb73ea64
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
<<<<<<< HEAD

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
    }

=======
>>>>>>> f9c06176761ab9c7bc48d0aa008b8fbddb73ea64
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
<<<<<<< HEAD
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
<<<<<<< HEAD
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    implementation("com.github.prolificinteractive:material-calendarview:1.4.3")

    // ✅ Firebase
=======

    implementation("com.github.prolificinteractive:material-calendarview:1.4.3")

    // ✅ Firestore + Firebase BOM 추가
>>>>>>> aa15941 (캘린더 + Firebase)
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-firestore-ktx")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
=======

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
    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth-ktx:23.2.1")
    implementation("com.google.firebase:firebase-database-ktx:21.0.0")

    implementation(libs.firebase.database)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.google.android.material:material:1.9.0")
}
>>>>>>> f9c06176761ab9c7bc48d0aa008b8fbddb73ea64
