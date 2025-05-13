package com.example.trenifyapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trenifyapp.data.AppDb
import com.example.trenifyapp.data.entities.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val appDb: AppDb,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val userId: Int = checkNotNull(savedStateHandle.get<String>("userId")?.toInt())
    val user = mutableStateOf<User?>(null)

    init {
        viewModelScope.launch {
            user.value = appDb.userDao.getById(userId)
        }
    }
}