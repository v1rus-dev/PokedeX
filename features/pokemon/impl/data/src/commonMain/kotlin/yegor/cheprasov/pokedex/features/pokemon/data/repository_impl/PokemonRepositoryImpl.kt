package yegor.cheprasov.pokedex.features.pokemon.data.repository_impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import yegor.cheprasov.pokedex.core.network.asResult
import yegor.cheprasov.pokedex.features.pokemon.data.datasource.NetworkPokemonDatasource
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonMapper
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val datasource: NetworkPokemonDatasource,
    private val pokemonMapper: PokemonMapper,
) : PokemonRepository {
    override suspend fun getPokemon(pokemonName: String) = datasource.getPokemon(pokemonName)
        .map {
            withContext(Dispatchers.Default) {
                pokemonMapper.map(it)
            }
        }
        .asResult()
}
