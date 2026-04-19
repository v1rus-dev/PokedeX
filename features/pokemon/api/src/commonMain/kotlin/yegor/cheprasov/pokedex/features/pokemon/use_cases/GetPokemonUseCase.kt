package yegor.cheprasov.pokedex.features.pokemon.use_cases

import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel

interface GetPokemonUseCase {
    suspend operator fun invoke(pokemonName: String): Result<PokemonModel>
}