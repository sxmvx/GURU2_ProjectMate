pluginManagement {
    repositories {
<<<<<<< HEAD
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
=======
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
        // kakao SDK를 받아올 저장소 주소 작성
    }
}

rootProject.name = "Projectmate"
include(":app")
 
>>>>>>> f9c06176761ab9c7bc48d0aa008b8fbddb73ea64
