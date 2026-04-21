package yegor.cheprasov.pokedex.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import yegor.cheprasov.pokedex.core.database.ability.AbilityDao
import yegor.cheprasov.pokedex.core.database.ability.entity.AbilityEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonAbilityCrossRefEntity
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonTypeDao
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEntity
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonDao
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeCrossRefEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeEntity

@Database(
    entities = [
        PokemonEntity::class,
        PokemonTypeEntity::class,
        PokemonTypeCrossRefEntity::class,
        AbilityEntity::class,
        PokemonAbilityCrossRefEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
@ConstructedBy(PokedexDatabaseConstructor::class)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    abstract fun pokemonTypeDao(): PokemonTypeDao

    abstract fun abilityDao(): AbilityDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "KotlinNoActualForExpect")
expect object PokedexDatabaseConstructor : RoomDatabaseConstructor<PokedexDatabase> {
    override fun initialize(): PokedexDatabase
}
