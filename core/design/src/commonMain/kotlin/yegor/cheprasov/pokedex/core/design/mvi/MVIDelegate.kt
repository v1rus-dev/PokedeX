package yegor.cheprasov.pokedex.core.design.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

interface StateUi
interface ActionUi
interface EventUi

interface MVI<State : StateUi, Action : ActionUi, Event : EventUi> {
    val uiState: StateFlow<State>
    val uiEvents: Flow<Event>

    fun onAction(action: Action)
}

abstract class MviViewModel<
        State : StateUi,
        Action : ActionUi,
        Event : EventUi
        >(
    initialState: State
) : ViewModel(), MVI<State, Action, Event> {

    @PublishedApi
    internal val uiStateMutable = MutableStateFlow(initialState)

    override val uiState: StateFlow<State> = uiStateMutable.asStateFlow()

    private val _uiEvents = MutableSharedFlow<Event>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    override val uiEvents: Flow<Event> = _uiEvents.asSharedFlow()

    override fun onAction(action: Action) {
        //TODO: Override if needed
    }

    protected inline fun updateState(transform: State.() -> State): State {
        return uiStateMutable.updateAndGet { current ->
            current.transform()
        }
    }

    protected suspend fun emitEvent(event: Event) {
        _uiEvents.emit(event)
    }

    protected fun sendEvent(event: Event): Boolean {
        return _uiEvents.tryEmit(event)
    }
}