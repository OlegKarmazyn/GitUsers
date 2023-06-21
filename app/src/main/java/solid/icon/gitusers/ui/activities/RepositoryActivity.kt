package solid.icon.gitusers.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import es.dmoral.toasty.Toasty
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import solid.icon.gitusers.data.repositories.users_data.Repository
import solid.icon.gitusers.data.view_models.RepositoryViewModel
import solid.icon.gitusers.ui.components.LoadMoreButton
import solid.icon.gitusers.ui.components.LoadingBox

class RepositoryActivity : ComponentActivity(), KodeinAware {

    override val kodein by kodein()
    private val viewModel: RepositoryViewModel by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setLoginByIntent(intent)

        setContent {
            val repositories by viewModel.repositories
            val isLoading by viewModel.isLoading
            val isListEmpty by viewModel.isListEmpty
            showToastIfEmptyList(isListEmpty, viewModel.login)
            RepositoryScreen(repositories, isLoading, viewModel.login,
                onLoadMore = {
                    viewModel.fetchUserRepositories()
                })

            val listState = rememberLazyListState()
            LaunchedEffect(listState) {
                val visibleItemCount = listState.layoutInfo.visibleItemsInfo.size
                val totalItemCount = listState.layoutInfo.totalItemsCount
                val firstVisibleItemIndex =
                    listState.layoutInfo.visibleItemsInfo.firstOrNull()?.index ?: 0

                if (visibleItemCount + firstVisibleItemIndex >= totalItemCount) {
                    viewModel.fetchUserRepositories()
                }
            }
        }
    }

    @Composable
    fun RepositoryScreen(
        repositories: List<Repository>, isLoading: Boolean, login: String,
        onLoadMore: () -> Unit
    ) {
        Scaffold(
            topBar = { RepositoryAppBar(onBackPressed = { onBackPressed() }, login = login) },
            content = {
                LazyColumn {
                    items(repositories.size) { id ->
                        RepositoryItem(repositories[id])
                    }
                    item {
                        LoadMoreButton(onLoadMore)
                    }
                }
            }
        )
        LoadingBox(isLoading)
    }

    @Composable
    fun RepositoryAppBar(onBackPressed: () -> Unit, login: String) {
        TopAppBar(
            title = { Text(text = "$login's Repository List") },
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
        val expanded = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded.value = !expanded.value }
                .padding(16.dp)
        ) {
            Text(
                text = repository.name,
                style = MaterialTheme.typography.h6
            )
            repository.description?.let { description ->
                Text(
                    text = description,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = if (expanded.value) Int.MAX_VALUE else 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    private fun showToastIfEmptyList(isListEmpty: Boolean, login: String) {
        if (isListEmpty)
            Toasty.warning(this, "$login doesn't have repositories").show()
    }
}
