package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.domain.exceptions.InvalidFieldValueException
import com.example.trenifyapp.domain.exceptions.OutOfBoundsException
import com.example.trenifyapp.domain.exceptions.RequiredFieldException
import javax.inject.Inject

class ValidateExerciseWeightUseCase @Inject constructor() {
    private val _minWeight: Float = 0f

    operator fun invoke(weightAsString: String) : Result<Unit> {
        if (weightAsString.isBlank()) {
            return Result.failure(RequiredFieldException())
        }

        try {
            val weightAsFloat = weightAsString.toFloat()

            if (weightAsFloat < _minWeight) {
                return Result.failure(OutOfBoundsException(_minWeight))
            }

            return Result.success(Unit)
        }
        catch (_: Exception) {
            return Result.failure(InvalidFieldValueException())
        }
    }
}