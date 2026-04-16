import extensions.androidConfig

plugins {
    id("android.base.config")
}

androidConfig {
    buildFeatures {
        compose = true
    }
}