package solid.icon.gitusers.data.repositories.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import solid.icon.gitusers.data.database.entities.RepositoryItem
import solid.icon.gitusers.data.database.entities.UserItem

interface ApiService {

    @GET("users")
    suspend fun getUsers(
        @Query("since") currentUser: Int,
        @Query("per_page") filesSize: Int
    ): List<UserItem>

    @GET("users/{login}/repos")
    suspend fun getUserRepositories(
        @Path("login") login: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<RepositoryItem>

}