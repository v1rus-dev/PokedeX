package yegor.cheprasov.pokedex.features.pokemon.data.repository_impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import yegor.cheprasov.pokedex.core.network.asResult
import yegor.cheprasov.pokedex.features.pokemon.data.datasource.NetworkPokemonDatasource
import yegor.cheprasov.pokedex.features.pokemon.data.mapper.PokemonMapper
import yegor.cheprasov.pokedex.features.pokemon.domain.repository.PokemonRepository
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel

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

    override suspend fun getAllPokemonsFromNetwork(): Result<List<PokemonModel>> {
        val allPokemonsResult = datasource.getAllPokemonList().getOrNull() ?: return Result.failure(
            IllegalStateException("Failed getting all pokemons list")
        )

        if (allPokemonsResult.results.isEmpty()) {
            return Result.failure(IllegalStateException("All pokemon list is empty"))
        }

        return try {
            val allPokemons = coroutineScope {
                allPokemonsResult.results.map {
                    async { getPokemon(it.name).getOrThrow() }
                }.awaitAll()
            }

            Result.success(allPokemons)
        } catch (e: Exception) {
            Result.failure(IllegalStateException("Can not getting pokemon"))
        }


    }
}
