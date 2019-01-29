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
    val helper = { println("Usage: java -jar analyzer.jar <project location> <output file>") }

    if (args.size != 2) {
        helper()
        return
    }

    val walker = File(args[0]).walk()
    val methodAnalyzers = listOf(UselessOperationAnalyzer(), SameOperandAnalyzer(), EmptyBlockAnalyzer(), ExpressionConstAnalyzer())
    val warnings = mutableListOf<Warning>()

    File(args[1]).printWriter().use { pw ->
        walker.filter { !it.isDirectory && it.extension == "java" }.forEach { file ->
            warnings.clear()
            val cu: CompilationUnit

            try {
                cu = JavaParser.parse(file)
            } catch (e: Exception) {
                return@forEach
            }

            cu.types.filter { it is ClassOrInterfaceDeclaration }.forEach { type ->
                pw.println("Checking class ${cu.packageDeclaration.map { it.name.toString() + "." }.orElse("")}${type.nameAsString} ...")

                if (!cu.packageDeclaration.isPresent) {
                    warnings.add(Warning("No package was specified"))
                }

                cu.imports.forEach {
                    if (it.isAsterisk) {
                        warnings.add(Warning("Wildcard import", it.getPositionAndDescription()))
                    }
                }

                methodAnalyzers.forEach { analyzer ->
                    type.methods.forEach { method ->
                        analyzer.reset()
                        method.body.ifPresent {
                            warnings.addAll(analyzer.go(it))
                        }
                    }
                }
            }

            pw.println("${warnings.size} warnings found:\n")
            pw.println(warnings.joinToString("\n", transform = Warning::print))
            pw.println()
        }
    }
}