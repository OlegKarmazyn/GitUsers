package solid.icon.gitusers.data.view_models

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import solid.icon.gitusers.data.database.entities.UserItem
import solid.icon.gitusers.data.repositories.UserRepository
import solid.icon.gitusers.ui.activities.RepositoryActivity

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    val users: MutableState<List<UserItem>> = mutableStateOf(emptyList())
    val isListEmpty: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = mutableStateOf(false)

    private val pageSize = 10
    private var lastUserId = 0

    init {
        loadMoreUsers()
    }

    fun loadMoreUsers() {
        getUsersFromDB()
    }

    private fun getUsersFromDB() {
        Log.e("getUsersFromDB", "start")
        startLoading()
        viewModelScope.launch {
            val incomeList = userRepository.getListOfUsers(lastUserId, pageSize)
            users.value += incomeList
            if (incomeList.isNotEmpty())
                lastUserId = incomeList.last().id  //increase fetching number
            else
                fetchUsersFromApi()

            finishLoading()
        }
    }

    private fun fetchUsersFromApi() {
        Log.e("fetchUsersFromApi", "start")
        viewModelScope.launch(Dispatchers.IO) {
            val incomeList = userRepository.getUsers(lastUserId, pageSize)
            if (!checkIfListEmpty(incomeList)) {
                saveDataToDB(incomeList)
                loadMoreUsers()
            }
        }
    }

    private suspend fun saveDataToDB(list: List<UserItem>) {
        Log.e("saveDataToDB", "start")
        userRepository.upsertList(list)
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

    private fun finishLoading() {
        isLoading.value = false
    }

    private fun startLoading() {
        isLoading.value = true
    }
}