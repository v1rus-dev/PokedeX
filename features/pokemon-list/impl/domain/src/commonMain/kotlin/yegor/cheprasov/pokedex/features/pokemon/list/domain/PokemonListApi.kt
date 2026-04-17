package yegor.cheprasov.pokedex.features.pokemon.list.domain

import yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories.PokemonListRepository

class PokemonListApi(
    private val repository: PokemonListRepository,
) {
    fun getPokemonNames(): List<String> = repository.getPokemonNames()

    fun getDebugSummary(): String = repository.getDataSourceDescription()
}
