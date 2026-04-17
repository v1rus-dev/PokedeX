package yegor.cheprasov.pokedex.features.pokemon.details.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
data class PokemonDetails(val pokemonName: String) : NavKey

val pokemonDetailsSerializersModule = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(PokemonDetails::class)
    }
}