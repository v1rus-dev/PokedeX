package yegor.cheprasov.pokedex.features.pokemon.use_cases

import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState

interface SyncPokemonsUseCase {

    operator fun invoke(): Flow<SyncAllPokemonsState>

}
