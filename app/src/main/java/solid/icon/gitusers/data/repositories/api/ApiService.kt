package solid.icon.gitusers.data.repositories.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import solid.icon.gitusers.data.repositories.users_data.User
import solid.icon.gitusers.data.repositories.users_data.Repository

interface ApiService {

    @GET("users")
    suspend fun getUsers(@Query("since") since: Int, @Query("per_page") perPage: Int): List<User>

    @GET("users/{login}/repos")
    suspend fun getUserRepositories(@Path("login") login: String): List<Repository>
}