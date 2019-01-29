package ru.ifmo.ctddev.verification.analyzer.analyzer

data class Warning(
    var name: String = "",
    var positionDescription: String? = null
)

fun Warning.print() = "Warning: $name.${if (positionDescription == null) "\n" else " $positionDescription"}"