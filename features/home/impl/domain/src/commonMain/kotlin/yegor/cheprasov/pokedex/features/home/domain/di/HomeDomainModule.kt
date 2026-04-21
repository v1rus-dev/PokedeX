package yegor.cheprasov.pokedex.features.home.domain.di

import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.home.domain.use_cases.GetRandomPokemonsUseCase
import yegor.cheprasov.pokedex.features.home.domain.use_cases.GetRandomPokemonsUseCaseImpl

val homeDomainModule = module {
    factory<GetRandomPokemonsUseCase> {
        GetRandomPokemonsUseCaseImpl(get())
    }
}