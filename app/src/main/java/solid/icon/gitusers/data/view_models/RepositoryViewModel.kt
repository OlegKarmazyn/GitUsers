package solid.icon.gitusers.data.view_models

import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import solid.icon.gitusers.data.Constants
import solid.icon.gitusers.data.repositories.DetailsRepository
import solid.icon.gitusers.data.repositories.users_data.Repository

class RepositoryViewModel(private val detailsRepository: DetailsRepository) : ViewModel() {

    val repositories: MutableState<List<Repository>> = mutableStateOf(emptyList())
    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isListEmpty: MutableState<Boolean> = mutableStateOf(false)
    var login = String()

    private fun fetchUserRepositories(login: String) {
        isLoading.value = true
        viewModelScope.launch {
            repositories.value = detailsRepository.getUserRepositories(login)
            checkIfListEmpty(repositories.value)
            isLoading.value = false
        }
    }

    private fun checkIfListEmpty(list: List<Repository>) {
        isListEmpty.value = list.isEmpty()
    }

    fun setLoginByIntent(intent: Intent) {
        clearData()
        login = intent.getStringExtra(Constants.loginName)!!
        //note: call once to fetch data
        fetchUserRepositories(login)
    }

    private fun clearData() {
        repositories.value = emptyList()
        isLoading.value = false
        isListEmpty.value = false
    }
}