package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.data.AppDb
import javax.inject.Inject

class GenerateWorkoutUseCase @Inject constructor(
    private val _appDb: AppDb
) {
    suspend operator fun invoke(userId: Int) {

    }
}