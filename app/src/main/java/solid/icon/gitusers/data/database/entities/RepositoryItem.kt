package solid.icon.gitusers.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepositoryItem(
    @ColumnInfo var name: String,
    @ColumnInfo var description: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}