package yegor.cheprasov.pokedex.features.pokemon.domain.use_cases

import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetPokemonUseCase

class GetPokemonUseCaseImpl(private val repository: PokemonRepository) : GetPokemonUseCase {
    override suspend fun invoke(pokemonName: String): Result<PokemonModel> = repository.getPokemon(pokemonName = pokemonName)
}