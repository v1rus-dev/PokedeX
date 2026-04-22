package yegor.cheprasov.pokedex.core.database.ability.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = AbilityEntity.TABLE_NAME)
data class AbilityEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val effect: String?,
    @ColumnInfo(name = "short_effect")
    val shortEffect: String?
) {
    companion object {
        const val TABLE_NAME = "abilities"
    }
}
