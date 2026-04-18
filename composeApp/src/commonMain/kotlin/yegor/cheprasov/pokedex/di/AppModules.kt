package yegor.cheprasov.pokedex.di

import org.koin.core.module.Module
import yegor.cheprasov.pokedex.core.database.di.databaseModule
import yegor.cheprasov.pokedex.core.network.di.networkModule

val appModules: List<Module> = mutableListOf<Module>().apply {
    add(navigatorModule)
    add(databaseModule)
    add(networkModule)
    addAll(featureModules)
}
