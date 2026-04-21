package yegor.cheprasov.pokedex.core.database.ability

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.core.database.ability.entity.AbilityEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonAbilityCrossRefEntity

@Dao
interface AbilityDao {
    @Query("SELECT * FROM abilities ORDER BY name ASC")
    suspend fun getAllAbilities(): List<AbilityEntity>

    @Query("SELECT * FROM abilities ORDER BY name ASC")
    fun observeAll(): Flow<List<AbilityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAbility(entity: AbilityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAllAbilities(entities: List<AbilityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPokemonLinks(entities: List<PokemonAbilityCrossRefEntity>)

    @Query("DELETE FROM pokemon_ability_links WHERE ability_name = :abilityName")
    suspend fun deletePokemonLinksByAbilityName(abilityName: String)

    @Query("DELETE FROM pokemon_ability_links WHERE pokemon_name = :pokemonName")
    suspend fun deletePokemonLinksByPokemonName(pokemonName: String)

    @Query("DELETE FROM pokemon_ability_links WHERE pokemon_name IN (:pokemonNames)")
    suspend fun deletePokemonLinksByPokemonNames(pokemonNames: List<String>)

    @Query("DELETE FROM pokemon_ability_links")
    suspend fun clearAllPokemonLinks()

    @Query("DELETE FROM abilities")
    suspend fun clearAllAbilities()

    @Query("SELECT COUNT(*) == 0 from ABILITIES")
    suspend fun databaseIsEmpty(): Boolean
}
