package ru.ifmo.ctddev.verification.analyzer

data class Warning(
    var name: String = "",
    var positionDescription: String? = null
)

fun Warning.print() = "Warning: $name $positionDescription"