package solid.icon.gitusers.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import solid.icon.gitusers.data.repositories.api.ApiClient
import solid.icon.gitusers.data.repositories.api.ApiService
import solid.icon.gitusers.data.repositories.users_data.User

class UserRepository {

    private val apiService: ApiService = ApiClient.getApiService()

    suspend fun getUsers(page: Int, pageSize: Int): List<User> = withContext(Dispatchers.IO) {
        return@withContext try {
            Log.e("page", page.toString())
            Log.e("pageSize", pageSize.toString())
            apiService.getUsers(page, pageSize)
        } catch (e: Exception) {
            emptyList()
        }
    }
}

