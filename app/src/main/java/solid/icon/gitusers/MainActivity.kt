package solid.icon.gitusers

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import solid.icon.gitusers.data.repositories.UserRepository
import solid.icon.gitusers.data.repositories.users_data.User
import solid.icon.gitusers.data.view_models.MainViewModel

class MainActivity : ComponentActivity() {
    private lateinit var userViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userRepository = UserRepository() // Создаем экземпляр UserRepository

        userViewModel = MainViewModel(userRepository)

        setContent {
            val users by userViewModel.users
            UserList(users)
        }
    }
}

@Composable
fun UserList(users: List<User>) {
    LazyColumn {
        items(users.size) { id ->
            UserCell(users[id])
        }
    }
}

@Composable
fun UserCell(user: User) {
    val imageBitmap: MutableState<ImageBitmap?> = remember { mutableStateOf(null) }

    LaunchedEffect(user.avatarUrl) {
        withContext(Dispatchers.IO) {
            try {
                val bitmap = Picasso.get().load(user.avatarUrl).get()
                if (bitmap != null) {
                    imageBitmap.value = bitmap.asImageBitmap() //todo: fix - doesn't download
                }
            } catch (e: Exception) {
                Log.e("Exception: ", e.toString())
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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