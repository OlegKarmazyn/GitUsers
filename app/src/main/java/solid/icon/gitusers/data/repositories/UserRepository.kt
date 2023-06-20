package solid.icon.gitusers.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import solid.icon.gitusers.data.repositories.api.ApiClient
import solid.icon.gitusers.data.repositories.api.ApiService
import solid.icon.gitusers.data.repositories.users_data.User

class UserRepository {

    private val apiService: ApiService = ApiClient.getApiService()

    suspend fun getUsers(currentUser: Int, filesSize: Int): List<User> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                apiService.getUsers(currentUser, filesSize)
            } catch (e: Exception) {
                emptyList()
            }
        }
}

