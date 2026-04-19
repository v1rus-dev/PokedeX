package yegor.cheprasov.pokedex.features.settings.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import pokedex.core.resources.generated.resources.Res
import pokedex.core.resources.generated.resources.app_appearance
import pokedex.core.resources.generated.resources.choose_theme
import pokedex.core.resources.generated.resources.settings
import pokedex.core.resources.generated.resources.theme_dark
import pokedex.core.resources.generated.resources.theme_dark_description
import pokedex.core.resources.generated.resources.theme_light
import pokedex.core.resources.generated.resources.theme_light_description
import pokedex.core.resources.generated.resources.theme_mode
import pokedex.core.resources.generated.resources.theme_system
import pokedex.core.resources.generated.resources.theme_system_description
import org.jetbrains.compose.resources.StringResource
import yegor.cheprasov.pokedex.core.design.composable.toolbars.PokedexTopAppBar
import yegor.cheprasov.pokedex.core.design.theme.PokedexTheme
import yegor.cheprasov.pokedex.features.settings.api.ThemeMode
import yegor.cheprasov.pokedex.features.settings.presentation.SettingsActionUi
import yegor.cheprasov.pokedex.features.settings.presentation.SettingsStateUi

@Composable
internal fun SettingsScreen(
    state: SettingsStateUi,
    onAction: (SettingsActionUi) -> Unit,
) {
    val colors = PokedexTheme.colors
    val spacing = PokedexTheme.spacing
    val radii = PokedexTheme.radii
    Scaffold(
        containerColor = colors.appBackground,
        topBar = {
            PokedexTopAppBar(title = stringResource(Res.string.settings))
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Surface(
                modifier = Modifier
                    .padding(horizontal = spacing.large, vertical = spacing.large)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(radii.large),
                color = colors.cardBackground,
                tonalElevation = spacing.xSmall,
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = colors.cardBorder,
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(spacing.xLarge),
                ) {
                    androidx.compose.material3.Text(
                        text = stringResource(Res.string.app_appearance),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    androidx.compose.material3.Text(
                        modifier = Modifier.padding(top = spacing.small),
                        text = stringResource(Res.string.choose_theme),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    androidx.compose.material3.Text(
                        modifier = Modifier.padding(top = spacing.xLarge),
                        text = stringResource(Res.string.theme_mode),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    themeModeItems().forEachIndexed { index, item ->
                        ThemeModeRow(
                            modifier = Modifier.padding(top = if (index == 0) spacing.medium else spacing.small),
                            title = stringResource(item.titleRes),
                            description = stringResource(item.descriptionRes),
                            selected = state.themeMode == item.themeMode,
                            onClick = {
                                onAction(SettingsActionUi.SelectThemeMode(item.themeMode))
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ThemeModeRow(
    title: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = PokedexTheme.spacing
    val radii = PokedexTheme.radii
    val colors = PokedexTheme.colors

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(radii.medium),
        color = if (selected) colors.listItemAccent else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = if (selected) colors.topAppBar.copy(alpha = 0.55f) else colors.cardBorder,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.large, vertical = spacing.medium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.padding(end = spacing.medium),
            ) {
                androidx.compose.material3.Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                )
                androidx.compose.material3.Text(
                    modifier = Modifier.padding(top = spacing.xSmall),
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Spacer(modifier = Modifier.width(spacing.medium))
            RadioButton(
                selected = selected,
                onClick = onClick,
            )
        }
    }
}

private data class ThemeModeItem(
    val themeMode: ThemeMode,
    val titleRes: StringResource,
    val descriptionRes: StringResource,
)

private fun themeModeItems(): List<ThemeModeItem> {
    return listOf(
        ThemeModeItem(
            themeMode = ThemeMode.System,
            titleRes = Res.string.theme_system,
            descriptionRes = Res.string.theme_system_description,
        ),
        ThemeModeItem(
            themeMode = ThemeMode.Light,
            titleRes = Res.string.theme_light,
            descriptionRes = Res.string.theme_light_description,
        ),
        ThemeModeItem(
            themeMode = ThemeMode.Dark,
            titleRes = Res.string.theme_dark,
            descriptionRes = Res.string.theme_dark_description,
        ),
    )
}
