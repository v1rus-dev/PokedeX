package yegor.cheprasov.pokedex.features.settings.api

import kotlinx.serialization.Serializable

@Serializable
enum class ThemeMode {
    System,
    Light,
    Dark,
}
