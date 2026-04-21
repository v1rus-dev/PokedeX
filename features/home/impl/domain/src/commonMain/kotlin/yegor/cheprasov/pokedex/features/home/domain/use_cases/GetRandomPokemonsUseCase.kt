package yegor.cheprasov.pokedex.features.home.domain.use_cases

import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetAllPokemonsUseCase
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import kotlin.collections.emptyList

private const val POKEMON_COUNT = 5

interface GetRandomPokemonsUseCase {
    suspend operator fun invoke(count: Int = POKEMON_COUNT) : Result<List<PokemonModel>>
}

class GetRandomPokemonsUseCaseImpl(
    private val getAllPokemonsUseCase: GetAllPokemonsUseCase,
) : GetRandomPokemonsUseCase {
    override suspend fun invoke(count: Int): Result<List<PokemonModel>> = getAllPokemonsUseCase().map { list ->
        if (list.isEmpty()) {
            emptyList()
        } else {
            list.shuffled().take(count)
        }
    }
}
