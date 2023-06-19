package solid.icon.gitusers.data.repositories.users_data

data class User(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val url: String,
    val html_url: String,
    val repos_url: String
)
