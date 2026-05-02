package yegor.cheprasov.pokedex.features.pokemon.ui.mappers

import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonEvolutionChainUiModel

class PokemonEvolutionChainModelToUiModelMapper(
    private val pokemonModelToUiModelMapper: PokemonModelToUiModelMapper,
) {
    fun map(input: List<PokemonModel>): PokemonEvolutionChainUiModel {
        return PokemonEvolutionChainUiModel(
            pokemons = input.map(pokemonModelToUiModelMapper::map),
        )
    }
}
