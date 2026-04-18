package yegor.cheprasov.pokedex.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import yegor.cheprasov.pokedex.core.database.favorites.FavoritePokemonDao
import yegor.cheprasov.pokedex.core.database.favorites.FavoritePokemonEntity
import yegor.cheprasov.pokedex.core.database.pokemon.PokemonDao

@Database(
    entities = [FavoritePokemonEntity::class],
    version = 1,
    exportSchema = true,
)
@ConstructedBy(PokedexDatabaseConstructor::class)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun favoritePokemonDao(): FavoritePokemonDao
    abstract fun pokemonDao(): PokemonDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "KotlinNoActualForExpect")
expect object PokedexDatabaseConstructor : RoomDatabaseConstructor<PokedexDatabase> {
    override fun initialize(): PokedexDatabase
}
