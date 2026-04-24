package yegor.cheprasov.pokedex.features.pokemon.domain.use_cases

import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonLiteModel
import yegor.cheprasov.pokedex.features.pokemon.use_cases.GetAllPokemonsUseCase

class GetAllPokemonsUseCaseImpl(private val repository: PokemonRepository) : GetAllPokemonsUseCase {
    override suspend fun invoke(): Result<List<PokemonLiteModel>> = repository.getAllPokemons()
}
