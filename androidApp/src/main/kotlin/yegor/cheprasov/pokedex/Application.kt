package yegor.cheprasov.pokedex

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import yegor.cheprasov.pokedex.di.initKoin
import yegor.cheprasov.pokedex.logging.AppLogger


class Application : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        AppLogger.init()
        initKoin {
            androidLogger()
            androidContext(this@Application)
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        ImageLoader(context).newBuilder()
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.1)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.03)
                    .directory(cacheDir)
                    .build()
            }
            .crossfade(true)
            .build()
}
