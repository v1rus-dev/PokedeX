package yegor.cheprasov.pokedex.features.pokemon.details.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.module
import org.koin.dsl.navigation3.navigation
import yegor.cheprasov.pokedex.core.design.animation.ProvideLocalAnimatedScope
import yegor.cheprasov.pokedex.core.design.navigation.AppNavigator
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.pokemon.details.api.PokemonDetails

@OptIn(KoinExperimentalAPI::class)
val pokemonDetailsPresentationModule = module {
    navigation<PokemonDetails> { route ->
        val navigator = get<AppNavigator>()
        ProvideLocalAnimatedScope {
            PokemonDetailsDestination(
                route = route,
                onBack = navigator::popBackStack,
            )
        }
    }
}

@Composable
private fun PokemonDetailsDestination(
    route: PokemonDetails,
    onBack: () -> Unit,
) {
    val spacing = PokedexTheme.spacing
    val radii = PokedexTheme.radii
    val colors = PokedexTheme.colors

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colors.appBackground,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(spacing.xLarge),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Surface(
                shape = androidx.compose.foundation.shape.RoundedCornerShape(radii.large),
                color = colors.cardBackground,
                border = androidx.compose.foundation.BorderStroke(1.dp, colors.cardBorder),
            ) {
                Column(
                    modifier = Modifier.padding(spacing.xLarge),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = route.pokemonName.replaceFirstChar { char -> char.uppercase() },
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        modifier = Modifier.padding(top = spacing.small),
                        text = "This screen is registered through Koin Navigation 3 and opens from the single root back stack.",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Button(
                        modifier = Modifier.padding(top = spacing.xLarge),
                        onClick = onBack,
                    ) {
                        Text(text = "Back")
                    }
                }
            }
        }
    }
}
