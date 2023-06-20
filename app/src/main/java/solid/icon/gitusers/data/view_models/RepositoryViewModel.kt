package solid.icon.gitusers.data.view_models

import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import solid.icon.gitusers.data.repositories.DetailsRepository
import solid.icon.gitusers.data.repositories.users_data.Repository

class RepositoryViewModel(private val detailsRepository: DetailsRepository) : ViewModel() {

    val repositories: MutableState<List<Repository>> = mutableStateOf(emptyList())
    private var loginName: String = "login"

    private fun fetchUserRepositories(login: String) {
        viewModelScope.launch {
            repositories.value = detailsRepository.getUserRepositories(login)
        }
    }

    fun setLoginByIntent(intent: Intent) {
        clearData()
        fetchUserRepositories(intent.getStringExtra(loginName)!!)
    }

    private fun clearData() {
        repositories.value = emptyList()
    }
}