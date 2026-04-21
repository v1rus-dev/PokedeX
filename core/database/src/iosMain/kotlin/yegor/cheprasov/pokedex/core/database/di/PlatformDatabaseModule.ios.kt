package yegor.cheprasov.pokedex.core.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask
import yegor.cheprasov.pokedex.core.database.PokedexDatabase
import yegor.cheprasov.pokedex.core.database.TransactionProvider
import yegor.cheprasov.pokedex.core.database.TransactionProviderIosImpl

internal actual val platformDatabaseModule: Module = module {
    single<RoomDatabase.Builder<PokedexDatabase>> {
        Room.databaseBuilder<PokedexDatabase>(
            name = documentDirectory() + "/$DATABASE_FILE_NAME",
        )
    }

    factory<TransactionProvider> {
        TransactionProviderIosImpl(get())
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val directory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )

    return requireNotNull(directory?.path)
}
