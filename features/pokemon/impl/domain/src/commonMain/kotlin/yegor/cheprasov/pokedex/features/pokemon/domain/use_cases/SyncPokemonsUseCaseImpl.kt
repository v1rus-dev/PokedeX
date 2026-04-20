package yegor.cheprasov.pokedex.features.pokemon.domain.use_cases

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.use_cases.SyncPokemonsUseCase
import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState

class SyncPokemonsUseCaseImpl(
    private val repository: PokemonRepository
) : SyncPokemonsUseCase {
    override fun invoke(): Flow<SyncAllPokemonsState> = repository.syncAllPokemons()
}
