package solid.icon.gitusers.data.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import solid.icon.gitusers.data.repositories.UserRepository
import solid.icon.gitusers.data.repositories.users_data.User

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    val users: MutableState<List<User>> = mutableStateOf(emptyList())

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            val userList = userRepository.getUsers()
            users.value = userList
        }
    }

    fun goToUserDetails(login: String) {
        TODO("Not yet implemented")
    }
}