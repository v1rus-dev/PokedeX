package yegor.cheprasov.pokedex.features.ability.use_cases

interface HasAbilitiesInDatabaseUseCase {
    suspend operator fun invoke(): Result<Boolean>
}