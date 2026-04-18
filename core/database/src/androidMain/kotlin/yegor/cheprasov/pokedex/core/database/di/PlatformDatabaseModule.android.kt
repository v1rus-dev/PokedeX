package yegor.cheprasov.pokedex.core.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import yegor.cheprasov.pokedex.core.database.PokedexDatabase

internal actual val platformDatabaseModule: Module = module {
    single<RoomDatabase.Builder<PokedexDatabase>> {
        val context: Context = get()
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(DATABASE_FILE_NAME)

        Room.databaseBuilder<PokedexDatabase>(
            context = appContext,
            name = dbFile.absolutePath,
        )
    }
}
