package solid.icon.gitusers.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import solid.icon.gitusers.data.repositories.api.ApiClient
import solid.icon.gitusers.data.repositories.users_data.Repository

class DetailsRepository {

    suspend fun getUserRepositories(login: String): List<Repository> = withContext(Dispatchers.IO) {
        return@withContext try {
            ApiClient.getApiService().getUserRepositories(login)
        } catch (e: Exception) {
            emptyList()
        }
    }

}