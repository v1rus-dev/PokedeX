package yegor.cheprasov.pokedex.features.home.presentation.di

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.home.presentation.HomeViewModel

val homePresentationModule = module {
    viewModelOf(::HomeViewModel)
}
