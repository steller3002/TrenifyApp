package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.domain.exceptions.OutOfBoundsException
import com.example.trenifyapp.domain.exceptions.InvalidFieldValueException
import com.example.trenifyapp.domain.exceptions.RequiredFieldException
import javax.inject.Inject

class ValidateAgeUseCase @Inject constructor() {
    private val _minAge = 8
    private val _maxAge = 120

    operator fun invoke(ageAsString: String): Result<Int> {
        if (ageAsString.isBlank()) {
            return Result.failure(RequiredFieldException())
        }

        try {
            val ageAsInt = ageAsString.toInt()

            if (ageAsInt !in _minAge.._maxAge) {
                return Result.failure(OutOfBoundsException(_minAge.toFloat(), _maxAge.toFloat()))
            }

            return Result.success(ageAsInt)
        }
        catch (_: Exception) {
            return Result.failure(InvalidFieldValueException())
        }
    }
}