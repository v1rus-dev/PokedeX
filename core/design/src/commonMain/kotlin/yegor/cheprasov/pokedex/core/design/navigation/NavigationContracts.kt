package yegor.cheprasov.pokedex.core.design.navigation

import androidx.compose.runtime.Composable
import androidx.savedstate.serialization.SavedStateConfiguration
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.plus

data class TopLevelDestinationSpec(
    val route: NavKey,
    val label: String,
    val iconLabel: String,
    val content: @Composable (AppNavigator) -> Unit,
)

fun navigationConfiguration(serializersModules: List<SerializersModule>): SavedStateConfiguration {
    val serializersModule = serializersModules
        .fold(SerializersModule { }) { acc, module ->
            acc + module
        }

    return SavedStateConfiguration {
        this.serializersModule = serializersModule
    }
}
