package yegor.cheprasov.pokedex.core.design.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
internal actual fun PokedexSystemBarsEffect(style: PokedexSystemBarsStyle) {
    val view = LocalView.current

    SideEffect {
        if (view.isInEditMode) {
            return@SideEffect
        }

        val activity = view.context.findActivity() ?: return@SideEffect
        val window = activity.window
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = style.navigationBarColor.toArgb()

        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = !style.preferLightStatusBarIcons
            isAppearanceLightNavigationBars = !style.preferLightNavigationBarIcons
        }
    }
}

private tailrec fun Context.findActivity(): Activity? {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> null
    }
}
