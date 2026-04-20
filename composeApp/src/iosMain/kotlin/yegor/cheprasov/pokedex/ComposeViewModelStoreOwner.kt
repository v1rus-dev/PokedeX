package yegor.cheprasov.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner

private class ComposeViewModelStoreOwner : ViewModelStoreOwner {
    override val viewModelStore: ViewModelStore = ViewModelStore()
}

@Composable
fun ProvideViewModelStoreOwner(
    content: @Composable () -> Unit,
) {
    val owner = remember { ComposeViewModelStoreOwner() }

    DisposableEffect(owner) {
        onDispose {
            owner.viewModelStore.clear()
        }
    }

    CompositionLocalProvider(
        LocalViewModelStoreOwner provides owner,
        content = content,
    )
}