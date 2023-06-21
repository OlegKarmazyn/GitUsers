package solid.icon.gitusers.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import solid.icon.gitusers.data.database.entities.RepositoryItem

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: RepositoryItem)

    @Delete
    suspend fun delete(item: RepositoryItem)

    @Query("SELECT * FROM RepositoryItem")
    fun getAllRepositoryItems(): LiveData<List<RepositoryItem>>
}