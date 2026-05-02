package yegor.cheprasov.pokedex.features.pokemon.domain.use_cases

import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetPokemonEvolutionChainUseCase

class GetPokemonEvolutionChainUseCaseImpl(
    private val repository: PokemonRepository,
) : GetPokemonEvolutionChainUseCase {
    override suspend fun invoke(pokemonName: String): Result<List<PokemonModel>> =
        repository.getEvolutionChain(pokemonName = pokemonName)
}
