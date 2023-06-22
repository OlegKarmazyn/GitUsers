package solid.icon.gitusers.data.view_models

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import solid.icon.gitusers.data.repositories.UserRepository
import solid.icon.gitusers.data.repositories.users_data.User
import solid.icon.gitusers.ui.activities.RepositoryActivity

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {

    val users: MutableState<List<User>> = mutableStateOf(emptyList())
    val isListEmpty: MutableState<Boolean> = mutableStateOf(false)
    val isLoading: MutableState<Boolean> = mutableStateOf(false)

    private val filesSize = 10
    private var currentUser = 0

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val incomeList = userRepository.getUsers(currentUser, filesSize)
            users.value += incomeList
            if (!checkIfListEmpty(incomeList))
                currentUser = incomeList.last().id
            isLoading.value = false
        }
    }

    fun loadMoreUsers() {
        fetchUsers()
    }

    private fun checkIfListEmpty(list: List<User>): Boolean {
        isListEmpty.value = list.isEmpty()
        return isListEmpty.value
    }

    fun goToUserDetails(login: String, context: Context) {
        val intent = Intent(context, RepositoryActivity::class.java)
        intent.putExtra("login", login)
        context.startActivity(intent)
    }
}