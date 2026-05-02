package yegor.cheprasov.pokedex.features.pokemon.use_cases

import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel

interface GetPokemonEvolutionChainUseCase {
    suspend operator fun invoke(pokemonName: String): Result<List<PokemonModel>>
}
