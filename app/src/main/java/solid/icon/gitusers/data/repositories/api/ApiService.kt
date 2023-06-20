package solid.icon.gitusers.data.repositories.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import solid.icon.gitusers.data.repositories.users_data.Repository
import solid.icon.gitusers.data.repositories.users_data.User

interface ApiService {

    @GET("users")
    suspend fun getUsers(
        @Query("since") currentUser: Int,
        @Query("per_page") filesSize: Int
    ): List<User>

    @GET("users/{login}/repos")
    suspend fun getUserRepositories(@Path("login") login: String): List<Repository>
}