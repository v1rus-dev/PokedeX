package yegor.cheprasov.pokedex.features.pokemon.data.mapper

import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.pokemon.data.models.PokemonResponse
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonSprites

class PokemonMapper : Mapper<PokemonResponse, PokemonModel> {
    override fun map(input: PokemonResponse): PokemonModel {
        return PokemonModel(
            id = input.id,
            name = input.name,
            sprites = PokemonSprites(
                backDefault = input.sprites.backDefault,
                frontDefault = input.sprites.frontDefault,
                backFemale = input.sprites.backFemale,
                frontFemale = input.sprites.frontFemale,
            ),
        )
    }
}
