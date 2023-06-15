package solid.icon.gitusers

import android.os.Bundle
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
import solid.icon.gitusers.data.repositories.models.User

//test data
val users = listOf(
    User(
        login = "mojombo1",
        id = 5,
        avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
        url = "https://api.github.com/users/mojombo",
        htmlUrl = "https://github.com/mojombo",
        reposUrl = "https://api.github.com/users/mojombo/repos"
    ),
    User(
        login = "mojombo2",
        id = 5,
        avatarUrl = "https://avatars.githubusercontent.com/u/2?v=4",
        url = "https://api.github.com/users/mojombo",
        htmlUrl = "https://github.com/mojombo",
        reposUrl = "https://api.github.com/users/mojombo/repos"
    )
)

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumn { //todo: redone using viewModel (could be LiveData)
                items(users.size) { id ->
                    UserCell(user = users[id])
                }
            }
        }
    }
}

@Composable
fun UserCell(user: User) {
    val imageBitmap: MutableState<ImageBitmap?> = remember { mutableStateOf(null) }

    LaunchedEffect(user.avatarUrl) {
        withContext(Dispatchers.IO) {
            val bitmap = Picasso.get().load(user.avatarUrl).get()
            imageBitmap.value = bitmap.asImageBitmap()
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