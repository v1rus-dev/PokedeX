package yegor.cheprasov.pokedex.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.MainViewModel

val composeAppModule: Module = module {
    viewModelOf(::MainViewModel)
}