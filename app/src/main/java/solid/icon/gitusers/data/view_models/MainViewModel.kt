package solid.icon.gitusers.data.view_models

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import solid.icon.gitusers.data.Constants.perPage
import solid.icon.gitusers.data.database.entities.UserItem
import solid.icon.gitusers.data.repositories.InternetConnection
import solid.icon.gitusers.data.repositories.UserRepository
import solid.icon.gitusers.ui.activities.RepositoryActivity

class MainViewModel(
    private val userRepository: UserRepository,
    private val connection: InternetConnection
) : ViewModel() {

    val users: MutableState<List<UserItem>> = mutableStateOf(emptyList())
    val isListEmpty: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = mutableStateOf(false)
    val isInternetConnected: MutableState<Boolean> = mutableStateOf(true)

    private var lastUserId = 0

    init {
        loadMoreUsers()
    }

    fun loadMoreUsers() {
        startLoading()
        viewModelScope.launch {
            val incomeList = getUsersFromDB()
            if (incomeList.isNotEmpty()) { //if no users in db
                updateUsersList(incomeList)
                lastUserId = incomeList.last().id
                finishLoading()
            } else {
                if (getInternetConnection())
                    fetchUsersFromApi()
                else
                    finishLoading()
            }
        }
    }

    private fun fetchUsersFromApi() {
        viewModelScope.launch(Dispatchers.IO) {
            val incomeList = userRepository.getUsers(lastUserId, perPage)
            if (!checkIfListEmpty(incomeList)) {
                saveDataToDB(incomeList)
                loadMoreUsers()
            }
            finishLoading()
        }
    }

    private suspend fun saveDataToDB(list: List<UserItem>) {
        userRepository.upsertList(list)
    }

    private suspend fun getUsersFromDB(): List<UserItem> {
        return userRepository.getListOfUsers(lastUserId, perPage)
    }

    private fun updateUsersList(incomeList: List<UserItem>) {
        users.value += incomeList
    }

    private fun checkIfListEmpty(list: List<UserItem>): Boolean {
        isListEmpty.value = list.isEmpty()
        return isListEmpty.value
    }

    fun goToUserDetails(login: String, context: Context) {
        val intent = Intent(context, RepositoryActivity::class.java)
        intent.putExtra("login", login)
        context.startActivity(intent)
    }

    private fun getInternetConnection(): Boolean {
        val isInternet = connection.isInternetConnected()
        isInternetConnected.value = isInternet
        return isInternet
    }

    private fun finishLoading() {
        isLoading.value = false
    }

    private fun startLoading() {
        isLoading.value = true
    }
}