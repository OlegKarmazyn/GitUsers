package solid.icon.gitusers.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import solid.icon.gitusers.data.repositories.api.ApiClient
import solid.icon.gitusers.data.repositories.users_data.User

class UserRepository {

    suspend fun getUsers(): List<User> = withContext(Dispatchers.IO) {
        return@withContext try {
            ApiClient.getApiService().getUsers()
        } catch (e: Exception) {
            emptyList()
        }
    }

}

