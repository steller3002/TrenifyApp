package com.example.trenifyapp.domain.exceptions

class OutOfBoundsException : Exception {
    constructor() : super()
    constructor(left: Float, right: Float) : super("Значение должно быть между $left и $right")
    constructor(left: Float) : super("Значение должно быть больше чем $left")
}