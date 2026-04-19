package yegor.cheprasov.pokedex

import androidx.compose.ui.window.ComposeUIViewController
import yegor.cheprasov.pokedex.logging.AppLogger

fun MainViewController() = ComposeUIViewController {
    AppLogger.init()
    PokedexApp()
}
