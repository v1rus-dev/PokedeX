package yegor.cheprasov.pokedex.core.design.navigation

import androidx.navigation3.runtime.NavKey

interface AppNavigator {
    val state: AppNavigationState
    fun navigate(route: NavKey)
    fun goBack()

    fun setNavigationState(state: AppNavigationState)
}

class AppNavigatorImpl : AppNavigator {

    private var mutableState: AppNavigationState? = null
    override val state: AppNavigationState get() = checkNotNull(mutableState) { "State is not initialized" }

    override fun navigate(route: NavKey) {
        state.backStack.add(route)
    }

    override fun goBack() {
        if (state.backStack.size > 1) {
            state.backStack.removeLastOrNull()
        }
    }

    override fun setNavigationState(state: AppNavigationState) {
        if (mutableState == null) {
            mutableState = state
        } else {
            throw IllegalStateException("You can set navigation state only one time")
        }
    }
}