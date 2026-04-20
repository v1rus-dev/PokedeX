package yegor.cheprasov.pokedex.features.search.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
data object PokemonSearch : NavKey

val pokemonSearchNavigationSerializersModule = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(PokemonSearch::class)
    }
}
