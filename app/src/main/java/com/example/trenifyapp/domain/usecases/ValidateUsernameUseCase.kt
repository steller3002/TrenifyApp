package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.domain.exceptions.RequiredFieldException
import javax.inject.Inject

class ValidateUsernameUseCase @Inject constructor() {
    operator fun invoke(username: String): Result<Unit> {
        if (username.isBlank()) {
            return Result.failure(RequiredFieldException())
        }

        return Result.success(Unit)
    }
}