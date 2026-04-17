package yegor.cheprasov.pokedex.features.pokemon.list.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.PokemonListActionUi
import yegor.cheprasov.pokedex.features.pokemon.list.presentation.PokemonListStateUi

@Composable
internal fun PokemonListScreen(
    state: PokemonListStateUi,
    onAction: (PokemonListActionUi) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Pokedex tab",
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "This screen lives inside the root Scaffold and nested Navigation 3 host.",
            textAlign = TextAlign.Center,
        )
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                onAction(PokemonListActionUi.ClickPokemon(name = "Bulbasaur"))
            },
        ) {
            Text("Open full screen")
        }
    }
}
