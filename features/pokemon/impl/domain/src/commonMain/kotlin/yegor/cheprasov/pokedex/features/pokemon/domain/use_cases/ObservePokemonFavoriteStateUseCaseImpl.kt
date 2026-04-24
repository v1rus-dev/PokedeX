package yegor.cheprasov.pokedex.features.pokemon.domain.use_cases

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.use_cases.ObservePokemonFavoriteStateUseCase

class ObservePokemonFavoriteStateUseCaseImpl(
    private val pokemonRepository: PokemonRepository
) : ObservePokemonFavoriteStateUseCase {
    override fun invoke(pokemonName: String): Flow<Boolean> = pokemonRepository.observePokemonIsFavorite(pokemonName.lowercase())
}