package yegor.cheprasov.pokedex.features.pokemon.ui.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonStats
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonStatsUiModel

class PokemonStatsModelToUiModel : Mapper<PokemonStats, PokemonStatsUiModel> {
    override fun map(input: PokemonStats): PokemonStatsUiModel =
        PokemonStatsUiModel.entries.firstOrNull { it.name == input.name }
            ?: PokemonStatsUiModel.Unknown
}
