package yegor.cheprasov.pokedex.features.ability.data.di

import org.koin.core.module.Module
import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.ability.data.datasource.LocalAbilityDatasource
import yegor.cheprasov.pokedex.features.ability.data.datasource.NetworkAbilityDatasource
import yegor.cheprasov.pokedex.features.ability.data.mapper.AbilityEntityMapper
import yegor.cheprasov.pokedex.features.ability.data.mapper.AbilityResponseMapper
import yegor.cheprasov.pokedex.features.ability.data.repositories.AbilityRepositoryImpl
import yegor.cheprasov.pokedex.features.ability.domain.repository.AbilityRepository

val abilityDataModule: Module = module {
    single<NetworkAbilityDatasource> {
        NetworkAbilityDatasource(get())
    }

    single<LocalAbilityDatasource> {
        LocalAbilityDatasource(get(), get())
    }

    factory { AbilityResponseMapper() }
    factory { AbilityEntityMapper() }

    single<AbilityRepository> {
        AbilityRepositoryImpl(
            localDatasource = get(),
            abilityEntityMapper = get(),
        )
    }
}
