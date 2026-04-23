package yegor.cheprasov.pokedex.features.pokemon.list.domain.di

import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.list.domain.use_cases.GetPokemonPagedUseCase
import yegor.cheprasov.pokedex.features.pokemon.list.domain.use_cases.GetPokemonPagedUseCaseImpl

val pokemonListDomainModule = module {
    factory<GetPokemonPagedUseCase> {
        GetPokemonPagedUseCaseImpl(
            pokemonListRepository = get(),
        )
    }
}
