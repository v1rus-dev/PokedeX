package yegor.cheprasov.pokedex.core.database.pokemon

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonEvolutionChainLinkEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonStatEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonStatRangeEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonTypeCrossRefEntity
import yegor.cheprasov.pokedex.core.database.pokemon.entity.PokemonWithRelationsEntity

@Dao
interface PokemonDao {
    @Query("SELECT EXISTS(SELECT 1 FROM pokemons)")
    suspend fun hasPokemons(): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM pokemon_evolution_chain_links)")
    suspend fun hasEvolutionChains(): Boolean

    @Transaction
    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun observeAll(): Flow<List<PokemonWithRelationsEntity>>

    @Transaction
    @Query("SELECT * FROM pokemons WHERE name = :name")
    fun observePokemon(name: String): Flow<PokemonWithRelationsEntity>

    @Transaction
    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    suspend fun getAllPokemons(): List<PokemonWithRelationsEntity>

    @Transaction
    @Query("SELECT * FROM pokemons ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPokemonsPage(limit: Int, offset: Int): List<PokemonWithRelationsEntity>

    @Transaction
    @Query("SELECT * FROM pokemons WHERE name LIKE '%' || :pokemonName || '%' ORDER BY name ASC LIMIT :limit OFFSET :offset")
    suspend fun searchByNamePage(pokemonName: String, limit: Int, offset: Int): List<PokemonWithRelationsEntity>

    @Transaction
    @Query("SELECT * FROM pokemons WHERE name LIKE '%' || :pokemonName || '%' ORDER BY name ASC")
    fun searchByName(pokemonName: String): Flow<List<PokemonWithRelationsEntity>>

    @Query("SELECT * FROM pokemons WHERE is_favorite = 1")
    suspend fun getFavoritePokemons(): List<PokemonEntity>

    @Query("UPDATE pokemons SET is_favorite = :isFavorite WHERE name = :pokemonName")
    suspend fun updateFavoriteState(pokemonName: String, isFavorite: Boolean)

    @Transaction
    @Query("SELECT * FROM pokemons WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): PokemonWithRelationsEntity?

    @Transaction
    @Query(
        """
        SELECT pokemons.* FROM pokemons
        INNER JOIN pokemon_evolution_chain_links links ON links.pokemon_name = pokemons.name
        WHERE links.chain_id = (
            SELECT chain_id FROM pokemon_evolution_chain_links
            WHERE pokemon_name = :pokemonName
            LIMIT 1
        )
        ORDER BY links.slot ASC
        """
    )
    suspend fun getEvolutionChainByPokemonName(pokemonName: String): List<PokemonWithRelationsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPokemon(entity: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAllPokemons(entities: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertEvolutionChainLinks(entities: List<PokemonEvolutionChainLinkEntity>)

    @Query("DELETE FROM pokemons")
    suspend fun clearAllPokemons()

    @Query("DELETE FROM pokemon_evolution_chain_links")
    suspend fun clearAllEvolutionChainLinks()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTypeLinks(entities: List<PokemonTypeCrossRefEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStats(entities: List<PokemonStatEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStatRanges(entities: List<PokemonStatRangeEntity>)

    @Query("SELECT stat, MIN(stat_value) AS min_value, MAX(stat_value) AS max_value FROM pokemon_stats GROUP BY stat")
    suspend fun getStatRanges(): List<PokemonStatRangeEntity>

    @Query("DELETE FROM pokemon_type_links WHERE pokemon_name = :pokemonName")
    suspend fun deleteTypeLinksByPokemonName(pokemonName: String)

    @Query("DELETE FROM pokemon_stats WHERE pokemon_name = :pokemonName")
    suspend fun deleteStatsByPokemonName(pokemonName: String)

    @Query("DELETE FROM pokemon_type_links")
    suspend fun clearAllTypeLinks()

    @Query("DELETE FROM pokemon_stats")
    suspend fun clearAllStats()

    @Query("DELETE FROM pokemon_stat_ranges")
    suspend fun clearAllStatRanges()
}
