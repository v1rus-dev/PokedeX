package yegor.cheprasov.pokedex.features.pokemon.list.presentation.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.models.PokemonUiModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel

class PokemonUiMapper : Mapper<PokemonModel, PokemonUiModel> {
    override fun map(input: PokemonModel): PokemonUiModel = PokemonUiModel(
        name = input.name,
        url = input.imageUrl
    )
}