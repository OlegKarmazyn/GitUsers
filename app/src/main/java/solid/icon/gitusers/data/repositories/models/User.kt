package solid.icon.gitusers.data.repositories.models

data class User(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val url: String,
    val htmlUrl: String,
    val reposUrl: String
)
