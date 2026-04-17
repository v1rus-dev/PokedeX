package yegor.cheprasov.pokedex.features.favorites.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
data object Favorites : NavKey

val favoritesNavigationSerializersModule = SerializersModule {
    polymorphic(NavKey::class) {
        subclass(Favorites::class)
    }
}
