package solid.icon.gitusers.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepositoryItem(
    @PrimaryKey var id: Int? = null,
    @ColumnInfo var name: String,
    @ColumnInfo var description: String?
)