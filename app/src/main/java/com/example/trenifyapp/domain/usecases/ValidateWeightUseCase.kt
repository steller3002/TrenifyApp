package com.example.trenifyapp.domain.usecases

import com.example.trenifyapp.domain.exceptions.ValueOutOfBoundsException
import com.example.trenifyapp.domain.exceptions.InvalidFieldValueException
import com.example.trenifyapp.domain.exceptions.RequiredFieldException
import javax.inject.Inject

class ValidateWeightUseCase @Inject constructor() {
    private val _minWeight = 10f
    private val _maxWeight = 450f

    operator fun invoke(weightAsString: String): Result<Unit> {
        if (weightAsString.isBlank()) {
            return Result.failure(RequiredFieldException())
        }

        try {
            val weightAsFloat = weightAsString.toFloat()

            if (weightAsFloat !in _minWeight.._maxWeight) {
                return Result.failure(ValueOutOfBoundsException(_minWeight, _maxWeight))
            }

            return Result.success(Unit)
        }
        catch (_: Exception) {
            return Result.failure(InvalidFieldValueException())
        }
    }
}