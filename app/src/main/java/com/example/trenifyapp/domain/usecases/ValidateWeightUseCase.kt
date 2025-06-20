package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.domain.exceptions.OutOfBoundsException
import com.example.trenifyapp.domain.exceptions.InvalidFieldValueException
import com.example.trenifyapp.domain.exceptions.RequiredFieldException
import javax.inject.Inject

class ValidateWeightUseCase @Inject constructor() {
    private val _minWeight = 10f
    private val _maxWeight = 450f

    operator fun invoke(weightAsString: String): Result<Float> {
        if (weightAsString.isBlank()) {
            return Result.failure(RequiredFieldException())
        }

        try {
            val weightAsFloat = weightAsString.toFloat()

            if (weightAsFloat !in _minWeight.._maxWeight) {
                return Result.failure(OutOfBoundsException(_minWeight, _maxWeight))
            }

            return Result.success(weightAsFloat)
        }
        catch (_: Exception) {
            return Result.failure(InvalidFieldValueException())
        }
    }
}