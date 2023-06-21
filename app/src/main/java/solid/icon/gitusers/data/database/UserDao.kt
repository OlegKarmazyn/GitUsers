package solid.icon.gitusers.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import solid.icon.gitusers.data.database.entities.UserItem

@Dao
interface UserDao {

    @Insert
    suspend fun insert(items: List<UserItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: UserItem)

    @Delete
    suspend fun delete(item: UserItem)

    @Query("SELECT * FROM UserItem")
    fun getAllUserItems(): LiveData<List<UserItem>>
}