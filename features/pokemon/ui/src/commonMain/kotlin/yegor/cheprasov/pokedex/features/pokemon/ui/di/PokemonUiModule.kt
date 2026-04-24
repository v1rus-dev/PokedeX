package yegor.cheprasov.pokedex.features.pokemon.ui.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonModel
import yegor.cheprasov.pokedex.features.pokemon.models.PokemonType
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonTypeModelToUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonModelToUiModelMapper
import yegor.cheprasov.pokedex.features.pokemon.ui.mappers.PokemonTypeUiModelToModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonTypeUiModel
import yegor.cheprasov.pokedex.features.pokemon.ui.models.PokemonUiModel

val pokemonUiModule = module {
    factoryOf(::PokemonTypeModelToUiModel) {
        bind<Mapper<PokemonType, PokemonTypeUiModel>>()
    }

    factoryOf(::PokemonModelToUiModelMapper) {
        bind<Mapper<PokemonModel, PokemonUiModel>>()
    }

    factoryOf(::PokemonTypeUiModelToModel) {
        bind<Mapper<PokemonTypeUiModel, PokemonType>>()
    }
}