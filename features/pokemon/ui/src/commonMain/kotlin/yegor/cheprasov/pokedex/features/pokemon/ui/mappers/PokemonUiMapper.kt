package yegor.cheprasov.pokedex.features.pokemon.ui.mappers

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonTypeUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

class PokemonUiMapper(
    private val pokemonTypeMapper: Mapper<PokemonType, PokemonTypeUiModel>
) : Mapper<PokemonModel, PokemonUiModel> {
    override fun map(input: PokemonModel): PokemonUiModel {
        return PokemonUiModel(
            name = input.name,
            pokemonTypes = input.types.map(pokemonTypeMapper::map)
        )
    }
}
