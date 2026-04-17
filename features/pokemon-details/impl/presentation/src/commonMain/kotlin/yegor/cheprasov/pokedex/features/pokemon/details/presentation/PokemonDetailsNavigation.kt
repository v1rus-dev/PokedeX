package yegor.cheprasov.pokedex.features.pokemon.details.presentation

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
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.features.pokemon.details.api.PokemonDetails

@OptIn(KoinExperimentalAPI::class)
val pokemonDetailsPresentationModule = module {
    navigation<PokemonDetails> { route ->
        val navigator = get<AppNavigator>()
        PokemonDetailsDestination(
            route = route,
            onBack = navigator::goBack,
        )
    }
}

@Composable
private fun PokemonDetailsDestination(
    route: PokemonDetails,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = route.pokemonName,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "This screen is registered through Koin Navigation 3 and opens from the single root back stack.",
            textAlign = TextAlign.Center,
        )
        Button(
            modifier = Modifier.padding(top = 24.dp),
            onClick = onBack,
        ) {
            Text(text = "Back")
        }
    }
}
