package ru.ifmo.ctddev.verification.analyzer

import com.github.javaparser.JavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import ru.ifmo.ctddev.verification.analyzer.analyzer.EmptyBlockAnalyzer
import ru.ifmo.ctddev.verification.analyzer.analyzer.ExpressionConstAnalyzer
import ru.ifmo.ctddev.verification.analyzer.analyzer.SameOperandAnalyzer
import ru.ifmo.ctddev.verification.analyzer.analyzer.UselessOperationAnalyzer
import ru.ifmo.ctddev.verification.analyzer.analyzer.Warning
import ru.ifmo.ctddev.verification.analyzer.analyzer.print
import java.io.File

fun main(args: Array<String>) {
    val helper = { println("Usage: java -jar analyzer.jar <project location> <output file> [-v]") }

    if (args.size !in 2..3) {
        helper()
        return
    }

    val walker = File(args[0]).walk()
    val methodAnalyzers = listOf(UselessOperationAnalyzer(), SameOperandAnalyzer(), EmptyBlockAnalyzer(), ExpressionConstAnalyzer())
    val globalWarnings = mutableListOf<Pair<String, Warning>>()
    val warnings = mutableListOf<Warning>()
    val verbose = args.size == 3 && args[2] == "-v"

    walker.filter { !it.isDirectory && it.extension == "java" }.forEach { file ->
        val cu: CompilationUnit

        try {
            cu = JavaParser.parse(file)
        } catch (e: Exception) {
            return@forEach
        }

        if (!cu.packageDeclaration.isPresent) {
            val w = Warning("No package was specified in ${file.canonicalPath}")

            if (verbose) {
                println(w.print())
            }

            globalWarnings.add("" to w)
        }

        cu.imports.forEach {
            if (it.isAsterisk) {
                val w = Warning("Wildcard import in ${file.canonicalPath}", it.getPositionAndDescription())

                if (verbose) {
                    println(w.print())
                }

                globalWarnings.add("" to w)
            }
        }

        cu.types.filter { it is ClassOrInterfaceDeclaration }.forEach { type ->
            warnings.clear()
            val className = "${cu.packageDeclaration.map { "${it.name}." }.orElse("")}${type.nameAsString}"

            if (verbose) {
                println("Checking class $className ...")
            }

            methodAnalyzers.forEach { analyzer ->
                type.methods.forEach { method ->
                    analyzer.reset()
                    method.body.ifPresent {
                        warnings.addAll(analyzer.go(it))
                    }
                }
            }

            if (verbose) {
                println("${warnings.size} warnings found:\n")
                println(warnings.joinToString("\n", transform = Warning::print))
                println()
            }

            globalWarnings.addAll(warnings.map { className to it })
        }
    }

    File(args[1]).printWriter().use { pw ->
        pw.println(buildString {
            append("${globalWarnings.size} warnings were found:\n\n")

            globalWarnings.forEach {
                if (it.first.isNotBlank()) {
                    append("Class ${it.first}: ")
                }

                append("${it.second.print()}\n")
            }
        }.trim())
    }
}