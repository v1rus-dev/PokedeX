package yegor.cheprasov.pokedex.features.search.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.search
import pokedex.core.resources.generated.resources.search_pokemon
import yegor.cheprasov.pokedex.core.design.animation.localSharedElement
import yegor.cheprasov.pokedex.core.design.composable.text_fields.TextField
import yegor.cheprasov.pokedex.core.design.composable.toolbars.PokedexTopAppBar
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
            PokedexTopAppBar(
                title = stringResource(Res.string.search),
                onBack = {
                    onAction.invoke(PokemonSearchActionUi.OnBackClicked)
                },
            )
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

            TextField(
                modifier = Modifier.localSharedElement(key = "home-search-text-field"),
                textFieldState = textFieldState,
                hint = stringResource(Res.string.search_pokemon),
            )

            Text(
                text = "Pokemon search module is connected. Domain and data layers are ready for implementation.",
                style = PokedexTheme.typography.bodyLarge,
                color = PokedexTheme.colors.textPrimary,
                textAlign = TextAlign.Start,
            )
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
