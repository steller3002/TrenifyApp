package com.example.trenifyapp.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.trenifyapp.data.AppDb
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountSelectionViewModel @Inject constructor(
    val appDb: AppDb
) : ViewModel() {
    val accounts = appDb.userDao.getAll()
    val selectedUserId = mutableStateOf<Int?>(null)

    fun changeSelectedUser(id: Int) {
        selectedUserId.value = id
    }
}