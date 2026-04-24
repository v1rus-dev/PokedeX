package yegor.cheprasov.pokedex.features.pokemon.details.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType

@Serializable
data class PokemonDetails(val pokemonName: String, val pokemonType: PokemonType) : NavKey

val pokemonDetailsSerializersModule = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(PokemonDetails::class)
    }
}