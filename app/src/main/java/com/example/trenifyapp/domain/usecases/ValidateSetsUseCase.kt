package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.domain.exceptions.InvalidFieldValueException
import com.example.trenifyapp.domain.exceptions.OutOfBoundsException
import com.example.trenifyapp.domain.exceptions.RequiredFieldException
import javax.inject.Inject

class ValidateSetsUseCase @Inject constructor() {
    private val _minSets: Int = 1
    private val _maxSets: Int = 6

    operator fun invoke(setsAsString: String): Result<Unit> {
        if (setsAsString.isBlank()) {
            return Result.failure(RequiredFieldException())
        }

        try {
            val setsAsInt = setsAsString.toInt()

            if (setsAsInt !in _minSets.._maxSets) {
                return Result.failure(OutOfBoundsException(_minSets.toFloat(), _maxSets.toFloat()))
            }

            return Result.success(Unit)
        }
        catch (_: Exception) {
            return Result.failure(InvalidFieldValueException())
        }
    }
}