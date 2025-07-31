pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
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

rootProject.name = "projectmate"
include(":app")
 