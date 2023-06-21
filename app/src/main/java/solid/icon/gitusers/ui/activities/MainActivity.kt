package solid.icon.gitusers.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import solid.icon.gitusers.data.repositories.users_data.User
import solid.icon.gitusers.data.view_models.MainViewModel
import solid.icon.gitusers.ui.components.LoadMoreButton
import solid.icon.gitusers.ui.components.LoadingBox

class MainActivity : ComponentActivity(), KodeinAware {

    override val kodein by kodein()
    private val viewModel: MainViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val users by viewModel.users
            val isLoading by viewModel.isLoading

            UserList(
                users = users,
                isLoading = isLoading,
                onClick = { login ->
                    viewModel.goToUserDetails(login, this)
                }, onLoadMore = {
                    viewModel.loadMoreUsers()
                })
        }
    }

    @Composable
    fun UserList(
        users: List<User>,
        isLoading: Boolean,
        onClick: (login: String) -> Unit,
        onLoadMore: () -> Unit
    ) {
        LazyColumn {
            items(users.size) { id ->
                val user = users[id]
                UserCell(user = user) {
                    onClick(user.login)
                }
            }
            item {
                LoadMoreButton(onLoadMore)
            }
        }
        LoadingBox(isLoading)
    }

    @Composable
    fun UserCell(user: User, onUserClick: () -> Unit) {
        val imageBitmap: MutableState<ImageBitmap?> = remember { mutableStateOf(null) }

        LaunchedEffect(user.avatar_url) {
            withContext(Dispatchers.IO) {
                val bitmap = Picasso.get().load(user.avatar_url).get()
                imageBitmap.value = bitmap.asImageBitmap()
            }
        }

        val modifier = Modifier
            .height(80.dp)
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onUserClick() }
            .background(Color.White, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            imageBitmap.value?.let { bitmap ->
                Image(
                    painter = BitmapPainter(bitmap),
                    contentDescription = "User Photo",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, MaterialTheme.colors.onSecondary, CircleShape)
                )
            }

            Text(
                text = user.login,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}