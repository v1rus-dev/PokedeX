package yegor.cheprasov.pokedex.features.favorites.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.favorites
import yegor.cheprasov.pokedex.core.design.composable.toolbars.RootPokedexTopAppBar

@Composable
internal fun PokemonFavoritesScreen() {
    Scaffold(
        topBar = {
            RootPokedexTopAppBar(title = stringResource(Res.string.favorites))
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {}
    }
}

