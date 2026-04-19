package yegor.cheprasov.pokedex

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import yegor.cheprasov.pokedex.di.appModules
import yegor.cheprasov.pokedex.logging.AppLogger


class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        AppLogger.init()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(appModules)
        }
    }

}
