package yegor.cheprasov.pokedex.features.pokemon.use_cases

interface HasPokemonsInDatabaseUseCase {
    suspend operator fun invoke(): Boolean
}
