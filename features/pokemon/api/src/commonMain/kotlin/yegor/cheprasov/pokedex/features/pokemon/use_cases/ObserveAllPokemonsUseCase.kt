package yegor.cheprasov.pokedex.features.pokemon.use_cases

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonLiteModel

interface ObserveAllPokemonsUseCase {
    operator fun invoke(): Flow<List<PokemonLiteModel>>
}
