package yegor.cheprasov.pokedex.features.home.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.ic_search
import pokedex.core.resources.generated.resources.search_pokemon
import yegor.cheprasov.pokedex.core.design.composable.text_fields.TextField
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.home.presentation.HomeActionUi
import yegor.cheprasov.pokedex.features.home.presentation.HomeStateUi

@Composable
internal fun HomeScreen(state: HomeStateUi, onAction: (HomeActionUi) -> Unit) {
    val scrollState = rememberScrollState()
    Scaffold(
        containerColor = PokedexTheme.colors.appBackground,
        contentWindowInsets = WindowInsets()
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(it).verticalScroll(scrollState)) {
            Spacer(modifier = Modifier.padding(top = 16.dp))
            TextField(
                enabled = false,
                modifier = Modifier.padding(horizontal = 16.dp), onClick = {
                    onAction.invoke(HomeActionUi.OnSearchClick)
                },
                hint = stringResource(Res.string.search_pokemon)
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    PokedexTheme {
        HomeScreen(state = HomeStateUi, onAction = {})
    }
}