pluginManagement {
    repositories {
        // ✅ 플러그인 적용에 필요한 저장소들
        google()
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://jitpack.io") // OK, 필요 시 유지
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // ✅ 의존성 가져올 저장소들
        google()
        mavenCentral()
        maven(url = "https://jitpack.io") // OK, 필요 시 유지
    }
}

rootProject.name = "GURU2_ProjectMate"
include(":app")
