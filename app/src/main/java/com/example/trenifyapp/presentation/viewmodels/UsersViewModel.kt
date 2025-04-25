package com.example.trenifyapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trenifyapp.data.AppDb
import com.example.trenifyapp.data.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    val appDb: AppDb
) : ViewModel() {
    val selectedUser = mutableStateOf<User?>(null)
    val usersList = mutableStateOf(emptyList<User>())

    fun loadUsers() {
        viewModelScope.launch {
            appDb.usersDao.getAll().collect { users ->
                usersList.value = users
            }
        }
    }

    fun selectUser(user: User) {
        selectedUser.value = user
    }
}