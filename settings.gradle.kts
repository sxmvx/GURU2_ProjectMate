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
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
        // kakao SDK를 받아올 저장소 주소 작성
    }
}

rootProject.name = "projectmate"
include(":app")
 