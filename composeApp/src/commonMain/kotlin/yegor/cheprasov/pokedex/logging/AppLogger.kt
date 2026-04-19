package yegor.cheprasov.pokedex.logging

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

object AppLogger {
    private var isInitialized = false

    fun init() {
        if (isInitialized) return

        Napier.base(DebugAntilog())
        isInitialized = true

        Napier.i(tag = "AppLogger") { "Napier initialized" }
    }
}
