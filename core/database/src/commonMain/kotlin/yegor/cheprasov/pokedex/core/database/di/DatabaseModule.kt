package yegor.cheprasov.pokedex.core.database.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.dsl.module
import yegor.cheprasov.pokedex.core.database.PokedexDatabase
import yegor.cheprasov.pokedex.core.database.TransactionProvider
import yegor.cheprasov.pokedex.core.database.TransactionProviderImpl

internal const val DATABASE_FILE_NAME: String = "pokedex.db"

val databaseModule: Module = module {
    includes(platformDatabaseModule)

    single<PokedexDatabase> {
        get<RoomDatabase.Builder<PokedexDatabase>>()
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }

    single {
        get<PokedexDatabase>().pokemonDao()
    }

    single {
        get<PokedexDatabase>().pokemonTypeDao()
    }

    factory<TransactionProvider> {
        TransactionProviderImpl(get())
    }
}

internal expect val platformDatabaseModule: Module
