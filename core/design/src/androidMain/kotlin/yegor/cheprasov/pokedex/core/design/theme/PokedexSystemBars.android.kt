package yegor.cheprasov.pokedex.core.design.theme

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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

@Composable
actual fun PokedexStatusBarEffect(preferLightIcons: Boolean) {
    val view = LocalView.current

    DisposableEffect(view, preferLightIcons) {
        if (view.isInEditMode) {
            return@DisposableEffect onDispose {}
        }

        val activity = view.context.findActivity()
        val window = activity?.window
        val insetsController = window?.let { WindowCompat.getInsetsController(it, view) }

        if (insetsController == null) {
            return@DisposableEffect onDispose {}
        }

        val previousLightStatusBars = insetsController.isAppearanceLightStatusBars
        insetsController.isAppearanceLightStatusBars = !preferLightIcons

        onDispose {
            insetsController.isAppearanceLightStatusBars = previousLightStatusBars
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
