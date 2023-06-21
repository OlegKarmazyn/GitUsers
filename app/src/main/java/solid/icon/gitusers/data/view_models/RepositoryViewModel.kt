package solid.icon.gitusers.data.view_models

import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import solid.icon.gitusers.data.Constants
import solid.icon.gitusers.data.Constants.perPage
import solid.icon.gitusers.data.repositories.DetailsRepository
import solid.icon.gitusers.data.repositories.users_data.Repository

class RepositoryViewModel(private val detailsRepository: DetailsRepository) : ViewModel() {

    val repositories: MutableState<List<Repository>> = mutableStateOf(emptyList())
    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isListEmpty: MutableState<Boolean> = mutableStateOf(false)

    var login = String()
    private var page = 1

    fun fetchUserRepositories() {
        startLoading()
        viewModelScope.launch {
            val incomeList = detailsRepository.getUserRepositories(login, page, perPage)
            repositories.value += incomeList
            checkIfListEmpty(incomeList)
            finishLoading()
            increasePage()
        }
    }

    private fun startLoading() {
        isLoading.value = true
    }

    private fun finishLoading() {
        isLoading.value = false
    }


    private fun increasePage() {
        page++
    }

    private fun checkIfListEmpty(list: List<Repository>) {
        isListEmpty.value = list.isEmpty()
    }

    fun setLoginByIntent(intent: Intent) {
        clearData()
        login = intent.getStringExtra(Constants.loginName)!!
        //note: call at once fetch data (first page)
        fetchUserRepositories()
    }

    private fun clearData() {
        repositories.value = emptyList()
        isLoading.value = false
        isListEmpty.value = false
        page = 1
    }
}