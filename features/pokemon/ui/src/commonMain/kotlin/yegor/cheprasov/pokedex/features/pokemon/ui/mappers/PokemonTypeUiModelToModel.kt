package yegor.cheprasov.pokedex.features.pokemon.ui.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonTypeUiModel

class PokemonTypeUiModelToModel : Mapper<PokemonTypeUiModel, PokemonType> {
    override fun map(input: PokemonTypeUiModel): PokemonType =
        PokemonType.entries.firstOrNull { it.name == input.name }
            ?: PokemonType.Unknown
}