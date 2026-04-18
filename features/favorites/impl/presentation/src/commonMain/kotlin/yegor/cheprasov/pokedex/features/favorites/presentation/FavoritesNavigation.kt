package yegor.cheprasov.pokedex.features.favorites.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.favorites
import yegor.cheprasov.pokedex.core.design.navigation.TopLevelDestinationSpec
import yegor.cheprasov.pokedex.features.favorites.api.Favorites

val favoritesTopLevelDestination = TopLevelDestinationSpec(
    route = Favorites,
    icon = Res.drawable.favorites,
    label = Res.string.favorites,
    content = { FavoritesDestination() },
)

@Composable
fun FavoritesDestination() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Favorites tab",
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "This screen is provided by the favorites feature presentation module.",
            textAlign = TextAlign.Center,
        )
    }
}
