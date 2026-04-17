package yegor.cheprasov.pokedex.core.design.composable.effects

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import yegor.cheprasov.pokedex.core.design.mvi.EventUi

@Composable
fun <Event : EventUi> CollectEventsUiEffect(
    eventsFlow: Flow<Event>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    onEvent: suspend CoroutineScope.(event: Event) -> Unit,
) {
    LaunchedEffect(eventsFlow, lifecycleOwner, minActiveState) {
        var collectJob: Job? = null

        lifecycleOwner.lifecycle.currentStateFlow.collectLatest { state ->
            val shouldCollect = state.isAtLeast(minActiveState)

            if (shouldCollect) {
                if (collectJob == null || collectJob?.isCancelled == true) {
                    collectJob = launch {
                        eventsFlow.collect { event ->
                            onEvent(event)
                        }
                    }
                }
            } else {
                collectJob?.cancel()
                collectJob = null
            }
        }
    }
}