package solid.icon.gitusers.ui.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
            RepositoryScreen(repositories)
        }
    }

    @Composable
    fun RepositoryScreen(repositories: List<Repository>) {
        LazyColumn {
            items(repositories.size) { id ->
                RepositoryItem(repositories[id])
            }
        }
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