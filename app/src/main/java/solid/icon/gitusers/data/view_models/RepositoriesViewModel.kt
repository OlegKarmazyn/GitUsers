package solid.icon.gitusers.data.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import solid.icon.gitusers.data.repositories.DetailsRepository
import solid.icon.gitusers.data.repositories.users_data.Repository

class RepositoriesViewModel(private val detailsRepository: DetailsRepository) : ViewModel() {

    val repositories: MutableState<List<Repository>> = mutableStateOf(emptyList())

    fun fetchUserRepositories(login: String) {
        viewModelScope.launch {
            repositories.value = detailsRepository.getUserRepositories(login)
        }
    }
}