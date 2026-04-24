package yegor.cheprasov.pokedex.features.pokemon.use_cases

import yegor.cheprasov.pokedex.features.pokemon.models.PokemonLiteModel

interface GetAllPokemonsUseCase {
    suspend operator fun invoke(): Result<List<PokemonLiteModel>>
}
