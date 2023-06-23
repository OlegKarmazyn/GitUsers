package solid.icon.gitusers.data.database

import androidx.room.*
import solid.icon.gitusers.data.database.entities.RepositoryItem

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertList(items: List<RepositoryItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: RepositoryItem)

    @Delete
    suspend fun delete(item: RepositoryItem)

    @Query("SELECT * FROM RepositoryItem WHERE login = :login AND name > :lastRepositoryName ORDER BY name ASC LIMIT :pageSize")
    suspend fun getRepositoriesWithPagination(
        login: String,
        lastRepositoryName: String,
        pageSize: Int
    ): List<RepositoryItem>

}