package solid.icon.gitusers.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import solid.icon.gitusers.data.database.UserDatabase
import solid.icon.gitusers.data.database.entities.UserItem
import solid.icon.gitusers.data.repositories.api.ApiClient
import solid.icon.gitusers.data.repositories.api.ApiService

class UserRepository(
    private val db: UserDatabase
) {

    private val apiService: ApiService = ApiClient.getApiService()

    suspend fun getUsers(currentUser: Int, filesSize: Int): List<UserItem> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                apiService.getUsers(currentUser, filesSize)
            } catch (e: Exception) {
                emptyList()
            }
        }

    suspend fun upsertList(list: List<UserItem>) = db.getUserDao().upsertList(list)

    suspend fun getListOfUsers(lastUserId: Int, pageSize: Int): List<UserItem> =
        db.getUserDao().getUsersWithPagination(lastUserId, pageSize)
}