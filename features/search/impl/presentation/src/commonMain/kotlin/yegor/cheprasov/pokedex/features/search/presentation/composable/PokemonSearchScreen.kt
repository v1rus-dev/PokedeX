package yegor.cheprasov.pokedex.features.search.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.search
import yegor.cheprasov.pokedex.core.design.composable.toolbars.PokedexSearchTopAppBar
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.search.presentation.PokemonSearchActionUi
import yegor.cheprasov.pokedex.features.search.presentation.PokemonSearchStateUi

@Composable
internal fun PokemonSearchScreen(
    state: PokemonSearchStateUi,
    onAction: (PokemonSearchActionUi) -> Unit,
) {
    val scrollState = rememberScrollState()
    val textFieldState = rememberTextFieldState()

    Scaffold(
        topBar = {
            PokedexSearchTopAppBar(
                textFieldState = textFieldState,
                hint = stringResource(Res.string.search),
                scrollState = scrollState,
                onBack = {
                    onAction(PokemonSearchActionUi.OnBackClicked)
                })
        },
        containerColor = PokedexTheme.colors.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = PokedexTheme.spacing.large)
                .verticalScroll(scrollState),
        ) {
            Spacer(modifier = Modifier.padding(top = PokedexTheme.spacing.large))
        }
    }
}

@Preview
@Composable
private fun PokemonSearchScreenPreview() {
    PokedexTheme {
        PokemonSearchScreen(
            state = PokemonSearchStateUi,
            onAction = {},
        )
    }
}
