package yegor.cheprasov.pokedex.features.ability.domain.di

import org.koin.dsl.module
import yegor.cheprasov.pokedex.features.ability.domain.use_cases.HasAbilitiesInDatabaseUseCaseImpl
import yegor.cheprasov.pokedex.features.ability.domain.use_cases.SyncAbilityUseCaseImpl
import yegor.cheprasov.pokedex.features.ability.use_cases.HasAbilitiesInDatabaseUseCase
import yegor.cheprasov.pokedex.features.ability.use_cases.SyncAbilityUseCase
import yegor.cheprasov.pokedex.features.sync.data.api.SyncDataUseCase

val abilityDomainModule = module {
    factory<HasAbilitiesInDatabaseUseCase> {
        HasAbilitiesInDatabaseUseCaseImpl(get())
    }

    factory<SyncAbilityUseCase> {
        SyncAbilityUseCaseImpl(get())
    }

    factory<SyncDataUseCase> {
        get<SyncAbilityUseCase>()
    }
}
