package ru.ifmo.ctddev.verification.analyzer.analyzer

import com.github.javaparser.ast.Node
import com.github.javaparser.ast.stmt.BlockStmt
import com.github.javaparser.ast.stmt.DoStmt
import com.github.javaparser.ast.stmt.EmptyStmt
import com.github.javaparser.ast.stmt.ForStmt
import com.github.javaparser.ast.stmt.ForeachStmt
import com.github.javaparser.ast.stmt.IfStmt
import com.github.javaparser.ast.stmt.Statement
import com.github.javaparser.ast.stmt.SynchronizedStmt
import com.github.javaparser.ast.stmt.TryStmt
import com.github.javaparser.ast.stmt.WhileStmt
import ru.ifmo.ctddev.verification.analyzer.getPositionAndDescription

class EmptyBlockAnalyzer : Analyzer {

    override fun go(stmt: Statement): List<Warning> {
        val warnings = mutableListOf<Warning>()
        EmptyBlockVisitor().visit(stmt, warnings)
        return warnings.map { it.name = warningName(); it }
    }

    override fun reset() = Unit

    override fun warningName() = "Empty block"
}

private class EmptyBlockVisitor {

    fun visit(stmt: Statement, warnings: MutableList<Warning>) {
        when (stmt) {
            is DoStmt -> checkEmpty(stmt.body)
            is ForStmt -> checkEmpty(stmt.body)
            is ForeachStmt -> checkEmpty(stmt.body)
            is IfStmt -> checkEmpty(stmt.thenStmt, stmt.elseStmt.orElse(null))
            is SynchronizedStmt -> checkEmpty(stmt.body)
            is TryStmt -> checkEmpty(stmt.tryBlock, *stmt.catchClauses.flatMap { it.childNodes }.toTypedArray())
            is WhileStmt -> checkEmpty(stmt.body)
            else -> null
        }?.let {
            warnings.addAll(it)
        }

        stmt.childNodes.forEach { node -> if (node is Statement) visit(node, warnings) }
    }

    private fun checkEmpty(vararg stmts: Node?): List<Warning> =
        stmts.mapNotNull { stmt ->
            when (stmt) {
                is BlockStmt -> checkEmptyBlock(stmt)
                else -> false
            }.let { if (it) Warning(positionDescription = stmt?.parentNode?.orElse(stmt)?.getPositionAndDescription()) else null }
        }

    private fun checkEmptyBlock(blockStmt: BlockStmt) = blockStmt.childNodes.all { it is EmptyStmt }
}