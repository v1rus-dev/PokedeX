package yegor.cheprasov.pokedex.features.pokemon.list.data.repositories_impl

import io.ktor.client.HttpClient
import yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories.PokemonListRepository

class PokemonListRepositoryImpl(
    private val httpClient: HttpClient,
) : PokemonListRepository {
    override fun getPokemonNames(): List<String> = listOf(
        "Bulbasaur",
        "Ivysaur",
        "Venusaur",
    )

    override fun getDataSourceDescription(): String =
        "PokemonListRepositoryImpl via HttpClient#${httpClient.hashCode()}"
}
