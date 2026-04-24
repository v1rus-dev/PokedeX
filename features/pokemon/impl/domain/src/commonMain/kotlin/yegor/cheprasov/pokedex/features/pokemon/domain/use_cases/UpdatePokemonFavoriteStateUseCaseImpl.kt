package yegor.cheprasov.pokedex.features.pokemon.domain.use_cases

import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.use_cases.UpdatePokemonFavoriteStateUseCase

class UpdatePokemonFavoriteStateUseCaseImpl(
    private val pokemonRepository: PokemonRepository
) : UpdatePokemonFavoriteStateUseCase {
    override suspend fun invoke(
        pokemonName: String,
        isFavorite: Boolean
    ): Result<Unit> = pokemonRepository.updateFavoriteState(pokemonName, isFavorite)
}