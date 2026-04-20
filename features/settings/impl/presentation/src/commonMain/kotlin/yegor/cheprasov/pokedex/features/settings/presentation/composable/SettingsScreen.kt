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
import yegor.cheprasov.pokedex.core.design.composable.toolbars.PokedexTopAppBar
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.settings.presentation.SettingsActionUi
import yegor.cheprasov.pokedex.features.settings.presentation.SettingsStateUi

@Composable
internal fun SettingsScreen(
    state: SettingsStateUi,
    onAction: (SettingsActionUi) -> Unit,
) {
    val colors = PokedexTheme.colors
    Scaffold(
        containerColor = colors.background,
        topBar = {
            PokedexTopAppBar(title = stringResource(Res.string.settings))
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {

        }
    }
}
