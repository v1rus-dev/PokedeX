package yegor.cheprasov.pokedex.features.pokemon.domain.use_cases

import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.use_cases.HasPokemonsInDatabaseUseCase

class HasPokemonsInDatabaseUseCaseImpl(
    private val repository: PokemonRepository,
) : HasPokemonsInDatabaseUseCase {
    override suspend fun invoke(): Boolean = repository.hasPokemons()
}
