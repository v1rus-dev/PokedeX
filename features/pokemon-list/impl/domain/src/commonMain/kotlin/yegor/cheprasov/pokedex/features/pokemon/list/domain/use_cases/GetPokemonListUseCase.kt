package yegor.cheprasov.pokedex.features.pokemon.list.domain.use_cases

import yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories.PokemonListRepository

interface GetPokemonListUseCase {
    suspend operator fun invoke()
}

class GetPokemonListUseCaseImpl(
    private val pokemonListRepository: PokemonListRepository,
) : GetPokemonListUseCase {
    override suspend fun invoke() {
        TODO("Not yet implemented")
    }
}