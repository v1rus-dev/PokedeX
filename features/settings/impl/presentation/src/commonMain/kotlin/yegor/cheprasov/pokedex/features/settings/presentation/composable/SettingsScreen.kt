package yegor.cheprasov.pokedex.features.settings.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.settings
import yegor.cheprasov.pokedex.core.design.composable.toolbars.RootPokedexTopAppBar

@Composable
internal fun SettingsScreen() {
    Scaffold(
        topBar = {
            RootPokedexTopAppBar(title = stringResource(Res.string.settings))
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {}
    }
}
