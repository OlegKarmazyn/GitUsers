package solid.icon.gitusers.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import solid.icon.gitusers.data.database.UserDatabase
import solid.icon.gitusers.data.database.entities.RepositoryItem
import solid.icon.gitusers.data.repositories.api.ApiClient
import solid.icon.gitusers.data.repositories.api.ApiService

class DetailsRepository(private val db: UserDatabase) {

    private val apiService: ApiService = ApiClient.getApiService()

    suspend fun getUserRepositories(
        login: String,
        page: Int,
        perPage: Int
    ): List<RepositoryItem> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                apiService.getUserRepositories(login, page, perPage)
            } catch (e: Exception) {
                emptyList()
            }
        }

    suspend fun upsertList(list: List<RepositoryItem>) = db.getRepositoryDao().upsertList(list)

    suspend fun getListOfRepositories(
        login: String,
        lastRepositoryName: String,
        pageSize: Int
    ): List<RepositoryItem> =
        db.getRepositoryDao().getRepositoriesWithPagination(login, lastRepositoryName, pageSize)
}