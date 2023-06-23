package solid.icon.gitusers.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import solid.icon.gitusers.data.database.entities.RepositoryItem
import solid.icon.gitusers.data.repositories.api.ApiClient

class DetailsRepository {

    suspend fun getUserRepositories(
        login: String,
        page: Int,
        perPage: Int
    ): List<RepositoryItem> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                ApiClient.getApiService().getUserRepositories(login, page, perPage)
            } catch (e: Exception) {
                emptyList()
            }
        }
}