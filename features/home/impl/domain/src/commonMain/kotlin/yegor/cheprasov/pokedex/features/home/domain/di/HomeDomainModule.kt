package yegor.cheprasov.pokedex.features.home.domain.di

import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.home.domain.use_cases.GetRandomPokemonsUseCase
import yegor.cheprasov.pokedex.features.home.domain.use_cases.GetRandomPokemonsUseCaseImpl
import yegor.cheprasov.pokedex.features.home.domain.use_cases.SyncUseCase
import yegor.cheprasov.pokedex.features.home.domain.use_cases.SyncUseCaseImpl
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataUseCase

val homeDomainModule = module {
    factory<GetRandomPokemonsUseCase> {
        GetRandomPokemonsUseCaseImpl(get())
    }

    single<SyncUseCase> {
        SyncUseCaseImpl(getAll<SyncDataUseCase>())
    }
}