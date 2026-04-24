package yegor.cheprasov.pokedex.features.pokemon.use_cases

interface UpdatePokemonFavoriteStateUseCase {
    suspend operator fun invoke(pokemonName: String, isFavorite: Boolean): Result<Unit>
}