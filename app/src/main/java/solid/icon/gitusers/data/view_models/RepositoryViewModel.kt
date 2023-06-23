package solid.icon.gitusers.data.view_models

import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import solid.icon.gitusers.data.Constants
import solid.icon.gitusers.data.Constants.perPage
import solid.icon.gitusers.data.database.entities.RepositoryItem
import solid.icon.gitusers.data.repositories.DetailsRepository

class RepositoryViewModel(private val detailsRepository: DetailsRepository) : ViewModel() {

    val repositories: MutableState<List<RepositoryItem>> = mutableStateOf(emptyList())
    val isListEmpty: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = mutableStateOf(false)

    var login: String = ""
    private var page: Int = 1
    private var lastRepositoryName: String = ""

    fun loadMoreRepositories() {
        getRepositoriesFromDB()
    }

    private fun getRepositoriesFromDB() {
        startLoading()
        viewModelScope.launch {
            val incomeList =
                detailsRepository.getListOfRepositories(login, lastRepositoryName, perPage)
            if (incomeList.isNotEmpty()) {
                increaseList(incomeList)
            } else {
                fetchRepositoriesFromApi()
            }
            finishLoading()
        }
    }

    private fun fetchRepositoriesFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            val incomeList = detailsRepository.getUserRepositories(login, page, perPage)
            if (!checkIfListEmpty(incomeList)) {
                saveDataToDB(incomeList)
                page++
            }
        }
    }

    private suspend fun saveDataToDB(list: List<RepositoryItem>) {
        list.forEach {
            it.login = login
        }
        detailsRepository.upsertList(list)
        increaseList(list)
    }

    private fun increaseList(incomeList: List<RepositoryItem>) {
        repositories.value += incomeList
        lastRepositoryName = incomeList.lastOrNull()?.name.orEmpty()
    }

    private fun startLoading() {
        isLoading.value = true
    }

    private fun finishLoading() {
        isLoading.value = false
    }

    private fun checkIfListEmpty(list: List<RepositoryItem>): Boolean {
        val isEmpty = list.isEmpty()
        isListEmpty.value = isEmpty
        return isEmpty
    }

    //initialization
    fun setLoginByIntent(intent: Intent) {
        clearData()
        login = intent.getStringExtra(Constants.loginName).orEmpty()
        loadMoreRepositories()
    }

    private fun clearData() {
        repositories.value = emptyList()
        isLoading.value = false
        isListEmpty.value = false
        page = 1
        lastRepositoryName = ""
    }
}