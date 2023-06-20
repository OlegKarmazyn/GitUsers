package solid.icon.gitusers.data.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import solid.icon.gitusers.data.repositories.DetailsRepository
import solid.icon.gitusers.data.repositories.users_data.Repository

class RepositoriesViewModel(private val detailsRepository: DetailsRepository) : ViewModel() {

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories

    fun fetchUserRepositories(login: String) {
        viewModelScope.launch {
            val repositories = detailsRepository.getUserRepositories(login)
            _repositories.value = repositories
        }
    }
}