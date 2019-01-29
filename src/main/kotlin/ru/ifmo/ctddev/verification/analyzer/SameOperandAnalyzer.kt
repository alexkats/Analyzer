package ru.ifmo.ctddev.verification.analyzer

import com.github.javaparser.ast.expr.AssignExpr
import com.github.javaparser.ast.expr.BinaryExpr
import com.github.javaparser.ast.stmt.Statement
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

class SameOperandAnalyzer : Analyzer {

    override fun go(stmt: Statement): List<Warning> {
        val warnings = mutableListOf<Warning>()
        stmt.accept(SameOperandVisitor(), warnings)
        return warnings.map { it.name = warningName(); it }
    }

    override fun reset() = Unit

    override fun warningName() = "Same operands"
}

private class SameOperandVisitor : VoidVisitorAdapter<MutableList<Warning>>() {

    override fun visit(n: AssignExpr?, arg: MutableList<Warning>?) {
        n?.operator?.let {
            if (n.target == n.value && n.operator != AssignExpr.Operator.PLUS && n.operator != AssignExpr.Operator.MULTIPLY) {
                arg?.add(Warning(positionDescription = n.getPositionAndDescription()))
            }
        }

        super.visit(n, arg)
    }

    override fun visit(n: BinaryExpr?, arg: MutableList<Warning>?) {
        n?.operator?.let {
            when (it) {
                BinaryExpr.Operator.PLUS,
                BinaryExpr.Operator.MULTIPLY,
                BinaryExpr.Operator.LEFT_SHIFT,
                BinaryExpr.Operator.SIGNED_RIGHT_SHIFT,
                BinaryExpr.Operator.UNSIGNED_RIGHT_SHIFT -> Unit
                else -> {
                    if (n.left == n.right) {
                        arg?.add(Warning(positionDescription = n.getPositionAndDescription()))
                    }
                }
            }
        }

        super.visit(n, arg)
    }
}