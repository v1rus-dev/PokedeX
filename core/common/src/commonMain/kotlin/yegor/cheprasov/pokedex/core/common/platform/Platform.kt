package yegor.cheprasov.pokedex.core.common.platform

enum class Platform {
    ANDROID,
    IOS,
}

expect val currentPlatform: Platform

val Platform.isAndroid: Boolean
    get() = this == Platform.ANDROID

val Platform.isIos: Boolean
    get() = this == Platform.IOS

