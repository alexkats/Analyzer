package ru.ifmo.ctddev.verification.analyzer

import com.github.javaparser.JavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import java.io.File

fun main(args: Array<String>) {
    val helper = { println("Usage: java -jar analyzer.jar <project location> <output file>") }

    if (args.size != 2) {
        helper()
        //        return
    }

    val walker = File(".").walk()
    val analyzers = listOf(UselessOperationAnalyzer(), SameOperandAnalyzer())
    val warnings = mutableListOf<Warning>()
    //    val walker = File(args[0]).walk()

    //    File(args[1]).printWriter().use { pw ->
    File("output.txt").printWriter().use { pw ->
        walker.filter { !it.isDirectory && it.extension == "java" }.forEach { file ->
            warnings.clear()
            val cu: CompilationUnit

            try {
                cu = JavaParser.parse(file)
            } catch (e: Exception) {
                return@forEach
            }

            pw.println("Checking ${file.canonicalPath} ...\n")

            analyzers.forEach { analyzer ->
                cu.types.filter { it is ClassOrInterfaceDeclaration }.forEach { type ->
                    type.methods.forEach { method ->
                        analyzer.reset()
                        method.body.ifPresent {
                            warnings.addAll(analyzer.go(it))
                        }
                    }
                }
            }

            pw.println(warnings.joinToString("\n", transform = Warning::print))
        }
    }
}