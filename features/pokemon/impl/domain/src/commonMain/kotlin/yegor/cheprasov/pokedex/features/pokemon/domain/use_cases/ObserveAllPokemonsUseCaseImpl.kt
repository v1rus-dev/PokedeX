package yegor.cheprasov.pokedex.features.pokemon.domain.use_cases

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.use_cases.ObserveAllPokemonsUseCase

class ObserveAllPokemonsUseCaseImpl(
    private val repository: PokemonRepository,
) : ObserveAllPokemonsUseCase {
    override fun invoke(): Flow<List<PokemonModel>> = repository.observeAllPokemons()
}
