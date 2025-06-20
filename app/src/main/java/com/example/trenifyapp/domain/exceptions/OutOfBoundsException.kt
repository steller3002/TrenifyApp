package com.example.trenifyapp.domain.exceptions

class OutOfBoundsException(left: Float, right: Float):
    Exception("Значение должно быть между $left и $right") {
}