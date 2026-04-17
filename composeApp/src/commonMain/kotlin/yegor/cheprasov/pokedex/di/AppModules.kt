package yegor.cheprasov.pokedex.di

import org.koin.core.module.Module
import yegor.cheprasov.pokedex.core.ktor.di.ktorModule

val appModules: List<Module> = mutableListOf<Module>().apply {
    add(navigatorModule)
    add(ktorModule)
    addAll(featureModules)
}
