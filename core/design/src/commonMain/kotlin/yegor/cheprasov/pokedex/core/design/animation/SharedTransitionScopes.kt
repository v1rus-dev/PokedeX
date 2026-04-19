package yegor.cheprasov.pokedex.core.design.animation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.navigation3.ui.LocalNavAnimatedContentScope

val LocalAnimatedScope = compositionLocalOf<AnimatedVisibilityScope?> { null }
val LocalSharedTransitionScope = compositionLocalOf<SharedTransitionScope?> { null }

@Composable
fun ProvideLocalAnimatedScope(content: @Composable () -> Unit) {
    val animatedScope = LocalNavAnimatedContentScope.current
    CompositionLocalProvider(LocalAnimatedScope provides animatedScope) {
        content()
    }
}

@Composable
fun ProvideLocalSharedTransitionScope(
    sharedTransitionScope: SharedTransitionScope,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalSharedTransitionScope provides sharedTransitionScope) {
        content()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun Modifier.localSharedElement(key: Any): Modifier = composed {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedScope = LocalAnimatedScope.current

    if (sharedTransitionScope == null || animatedScope == null) {
        return@composed this
    }

    with(sharedTransitionScope) {
        sharedElement(
            sharedContentState = rememberSharedContentState(key = key),
            animatedVisibilityScope = animatedScope,
        )
    }
}
