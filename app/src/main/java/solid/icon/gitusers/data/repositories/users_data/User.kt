package solid.icon.gitusers.data.repositories.users_data

data class User(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val url: String,
    val htmlUrl: String,
    val reposUrl: String
)
