package ru.ifmo.ctddev.verification.analyzer

import com.github.javaparser.ast.stmt.Statement

interface Analyzer {

    fun go(stmt: Statement): List<Warning>

    fun reset()

    fun warningName(): String
}