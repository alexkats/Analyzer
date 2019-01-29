package ru.ifmo.ctddev.verification.analyzer

import com.github.javaparser.Position
import com.github.javaparser.ast.Node
import com.github.javaparser.ast.nodeTypes.NodeWithRange
import java.util.Optional

fun NodeWithRange<Node>.getPositionAndDescription(customDescription: String = ""): String {
    val lineAndColumn: (Optional<Position>) -> String = { p ->
        p.map { "Line: ${it.line}, column: ${it.column}" }.orElse("Position is unknown")
    }

    return buildString {
        append("From ${lineAndColumn(begin)} ")
        append("to ${lineAndColumn(end)}")
        append("\nStatement: \"${this@getPositionAndDescription.toString().trim()}\"\n")

        if (customDescription.isNotBlank()) {
            append("$customDescription\n")
        }
    }
}