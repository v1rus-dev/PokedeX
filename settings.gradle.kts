rootProject.name = "pokedex"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

includeBuild("build-logic/convention")

include(":androidApp")
include(":composeApp")

/** Core */
include(":core:common")
include(":core:config")
include(":core:database")
include(":core:network:api")
include(":core:network:impl")
include(":core:design")
include(":core:resources")

/** Features */
include(":features:pokemon:api")
include(":features:pokemon:ui")
include(":features:pokemon:impl:data")
include(":features:pokemon:impl:domain")
include(":features:pokemon-list:api")
include(":features:pokemon-list:impl:data")
include(":features:pokemon-list:impl:domain")
include(":features:pokemon-list:impl:presentation")
// Root
include(":features:root:api")
include(":features:root:impl:presentation")
// Search
include(":features:search:api")
include(":features:search:impl:data")
include(":features:search:impl:domain")
include(":features:search:impl:presentation")
// Settings
include(":features:settings:api")
include(":features:settings:impl:data")
include(":features:settings:impl:domain")
include(":features:settings:impl:presentation")
// Favorites
include(":features:favorites:api")
include(":features:favorites:impl:data")
include(":features:favorites:impl:domain")
include(":features:favorites:impl:presentation")
// Home
include(":features:home:api")
include(":features:home:impl:presentation")
// Pokemon details
include(":features:pokemon-details:api")
include(":features:pokemon-details:impl:data")
include(":features:pokemon-details:impl:domain")
include(":features:pokemon-details:impl:presentation")
