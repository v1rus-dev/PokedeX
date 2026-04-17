package yegor.cheprasov.pokedex.features.pokemon.list.domain.repositories

interface PokemonListRepository {
    fun getPokemonNames(): List<String>

    fun getDataSourceDescription(): String
}
