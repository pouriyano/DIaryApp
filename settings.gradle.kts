pluginManagement {
    repositories {
        maven("https://maven.myket.ir")
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.myket.ir")
    }
}

rootProject.name = "Diary"
include(":app")