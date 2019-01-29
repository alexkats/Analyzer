package ru.ifmo.ctddev.verification.analyzer

import com.github.javaparser.ast.expr.BinaryExpr
import com.github.javaparser.ast.expr.Expression
import com.github.javaparser.ast.expr.IntegerLiteralExpr
import com.github.javaparser.ast.stmt.Statement
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

class UselessOperationAnalyzer : Analyzer {

    override fun go(stmt: Statement): List<Warning> {
        val warnings = mutableListOf<Warning>()
        stmt.accept(UselessOperationVisitor(), warnings)
        return warnings.map { it.name = warningName(); it }
    }

    override fun reset() = Unit

    override fun warningName() = "Useless operation"
}

private class UselessOperationVisitor : VoidVisitorAdapter<MutableList<Warning>>() {

    override fun visit(n: BinaryExpr?, arg: MutableList<Warning>?) {
        n?.operator?.let {
            when (it) {
                BinaryExpr.Operator.BINARY_AND,
                BinaryExpr.Operator.BINARY_OR,
                BinaryExpr.Operator.LEFT_SHIFT,
                BinaryExpr.Operator.SIGNED_RIGHT_SHIFT,
                BinaryExpr.Operator.UNSIGNED_RIGHT_SHIFT -> {
                    if (n.left.checkZero() || n.right.checkZero()) {
                        arg?.add(Warning(positionDescription = n.getPositionAndDescription()))
                    }
                }
                else -> Unit
            }
        }

        super.visit(n, arg)
    }

    private fun Expression.checkZero() = this is IntegerLiteralExpr && value == "0"
}