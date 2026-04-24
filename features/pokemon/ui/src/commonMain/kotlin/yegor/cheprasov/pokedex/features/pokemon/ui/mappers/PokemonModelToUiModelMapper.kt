package yegor.cheprasov.pokedex.features.pokemon.ui.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

class PokemonModelToUiModelMapper(
    private val pokemonTypeMapper: PokemonTypeModelToUiModel
) : Mapper<PokemonModel, PokemonUiModel> {
    override fun map(input: PokemonModel): PokemonUiModel {
        return PokemonUiModel(
            name = input.name,
            id = input.id,
            imageUrl = input.imageUrl ?: "",
            pokemonTypes = input.types.map(pokemonTypeMapper::map)
        )
    }
}
