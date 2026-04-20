package yegor.cheprasov.pokedex

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import yegor.cheprasov.pokedex.di.initKoin
import yegor.cheprasov.pokedex.logging.AppLogger


class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        AppLogger.init()
        initKoin {
            androidLogger()
            androidContext(this@Application)
        }
    }

}
