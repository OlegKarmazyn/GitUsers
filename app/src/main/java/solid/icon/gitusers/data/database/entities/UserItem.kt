package solid.icon.gitusers.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserItem(
    @PrimaryKey var id: Int,
    @ColumnInfo var login: String,
    @ColumnInfo var avatar_url: String
)