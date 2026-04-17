package yegor.cheprasov.pokedex.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigatorImpl

val navigatorModule: Module = module {
    singleOf(::AppNavigatorImpl) { bind<AppNavigator>() }
}
