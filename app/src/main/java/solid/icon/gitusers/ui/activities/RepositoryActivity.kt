package solid.icon.gitusers.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import solid.icon.gitusers.data.repositories.users_data.Repository
import solid.icon.gitusers.data.view_models.RepositoryViewModel

class RepositoryActivity : ComponentActivity(), KodeinAware {

    override val kodein by kodein()
    private val viewModel: RepositoryViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setLoginByIntent(intent)

        setContent {
            val repositories by viewModel.repositories
            val isLoading by viewModel.isLoading
            RepositoryScreen(repositories, isLoading, viewModel.login)
        }
    }

    @Composable
    fun RepositoryScreen(repositories: List<Repository>, isLoading: Boolean, login: String) {
        Scaffold(
            topBar = { RepositoryAppBar(onBackPressed = { onBackPressed() }, login = login) },
            content = {
                LazyColumn {
                    items(repositories.size) { id ->
                        RepositoryItem(repositories[id])
                    }
                }
            }
        )
        LoadingBox(isLoading)
    }

    @Composable
    fun LoadingBox(isLoading: Boolean) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }

    @Composable
    fun RepositoryAppBar(onBackPressed: () -> Unit, login: String) {
        TopAppBar(
            title = { Text(text = "$login Repository List") },
            navigationIcon = {
                IconButton(
                    onClick = { onBackPressed() }
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }

    @Composable
    fun RepositoryItem(repository: Repository) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = repository.name, style = MaterialTheme.typography.h6)
            repository.description?.let {
                Text(text = repository.description, style = MaterialTheme.typography.body1)
            }
        }
    }
}
