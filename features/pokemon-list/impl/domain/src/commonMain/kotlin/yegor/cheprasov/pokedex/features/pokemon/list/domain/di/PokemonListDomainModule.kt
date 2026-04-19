package yegor.cheprasov.pokedex.features.pokemon.list.domain.di

import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.pokemon.list.domain.use_cases.GetPokemonListUseCase
import yegor.cheprasov.pokedex.features.pokemon.list.domain.use_cases.GetPokemonListUseCaseImpl

val pokemonListDomainModule = module {
    factory<GetPokemonListUseCase> {
        GetPokemonListUseCaseImpl(
            pokemonListRepository = get(),
        )
    }
}
