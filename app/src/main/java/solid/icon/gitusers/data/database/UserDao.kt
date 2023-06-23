package solid.icon.gitusers.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import solid.icon.gitusers.data.database.entities.UserItem

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertList(items: List<UserItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: UserItem)

    @Delete
    suspend fun delete(item: UserItem)

    @Query("SELECT * FROM UserItem WHERE id > :lastUserId ORDER BY id ASC LIMIT :pageSize")
    suspend fun getUsersWithPagination(lastUserId: Int, pageSize: Int): List<UserItem>

}