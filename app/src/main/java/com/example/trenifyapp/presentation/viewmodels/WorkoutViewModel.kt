package com.example.trenifyapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.trenifyapp.data.AppDb
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    val _appDb: AppDb
) : ViewModel() {

}