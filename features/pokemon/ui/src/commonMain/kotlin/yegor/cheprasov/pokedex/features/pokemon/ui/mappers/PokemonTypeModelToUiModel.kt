package yegor.cheprasov.pokedex.features.pokemon.ui.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonTypeUiModel

class PokemonTypeModelToUiModel : Mapper<PokemonType, PokemonTypeUiModel> {
    override fun map(input: PokemonType): PokemonTypeUiModel =
        PokemonTypeUiModel.entries.firstOrNull { it.name == input.name }
            ?: PokemonTypeUiModel.Unknown
}