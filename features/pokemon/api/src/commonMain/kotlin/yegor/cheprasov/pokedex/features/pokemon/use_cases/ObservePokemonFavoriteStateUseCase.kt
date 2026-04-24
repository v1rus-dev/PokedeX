package yegor.cheprasov.pokedex.features.pokemon.use_cases

import kotlinx.coroutines.flow.Flow

interface ObservePokemonFavoriteStateUseCase {
    operator fun invoke(pokemonName: String): Flow<Boolean>
}