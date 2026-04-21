package yegor.cheprasov.pokedex.features.home.presentation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.home.presentation.HomeViewModel
import yegor.cheprasov.pokedex.features.home.presentation.mappers.SyncAllPokemonsStateToModelUiMapper
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi
import yegor.cheprasov.pokedex.features.pokemon.models.SyncAllPokemonsState

val homePresentationModule = module {
    viewModelOf(::HomeViewModel)

    factoryOf<Mapper<SyncAllPokemonsState, SyncAllPokemonsStateModelUi> >(::SyncAllPokemonsStateToModelUiMapper)
}
