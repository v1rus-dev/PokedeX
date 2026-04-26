package yegor.cheprasov.pokedex.features.pokemon.ui.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonStatModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonStatValueUiModel

class PokemonStatModelToValueUiModel(
    private val pokemonStatModelToUiModel: PokemonStatsModelToUiModel
) : Mapper<PokemonStatModel, PokemonStatValueUiModel> {
    override fun map(input: PokemonStatModel): PokemonStatValueUiModel = PokemonStatValueUiModel(
        statsUiModel = pokemonStatModelToUiModel.map(input.stat),
        value = input.statValue,
        minValue = input.minValue,
        maxValue = input.maxValue,
    )
}
