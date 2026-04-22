package yegor.cheprasov.pokedex.features.home.presentation.di

import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.core.common.mapper.Mapper
import yegor.cheprasov.pokedex.features.home.presentation.HomeViewModel
import yegor.cheprasov.pokedex.features.home.presentation.mappers.SyncDataStateToModelUiMapper
import yegor.cheprasov.pokedex.features.home.presentation.models.SyncAllPokemonsStateModelUi
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataState

val homePresentationModule = module {
    viewModelOf(::HomeViewModel)

    factoryOf<Mapper<SyncDataState, SyncAllPokemonsStateModelUi> >(::SyncDataStateToModelUiMapper)
}
