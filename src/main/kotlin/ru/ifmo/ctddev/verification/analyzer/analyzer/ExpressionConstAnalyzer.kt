package ru.ifmo.ctddev.verification.analyzer.analyzer

import com.github.javaparser.ast.expr.BinaryExpr
import com.github.javaparser.ast.expr.EnclosedExpr
import com.github.javaparser.ast.expr.Expression
import com.github.javaparser.ast.expr.UnaryExpr
import com.github.javaparser.ast.stmt.IfStmt
import com.github.javaparser.ast.stmt.Statement
import ru.ifmo.ctddev.verification.analyzer.getPositionAndDescription

class ExpressionConstAnalyzer : Analyzer {

    override fun go(stmt: Statement): List<Warning> {
        val warnings = mutableListOf<Warning>()
        ExpressionConstVisitor().visit(stmt, warnings)
        return warnings.map { it.name = warningName(); it }
    }

    override fun reset() = Unit

    override fun warningName() = "Expression has always the same value"
}

class ExpressionConstVisitor {

    private val expressions = mutableSetOf<Expression>()

    fun visit(stmt: Statement, warnings: MutableList<Warning>) {
        val newExpressions = mutableSetOf<Expression>()

        when (stmt) {
            is IfStmt -> checkIfCondition(stmt, newExpressions)
            else -> null
        }?.let {
            warnings.addAll(it)
        }

        expressions.addAll(newExpressions)
        stmt.childNodes.forEach { node -> if (node is Statement) visit(node, warnings) }
        expressions.removeAll(newExpressions)
    }

    private fun checkIfCondition(stmt: IfStmt, newExpressions: MutableSet<Expression>): List<Warning> {
        val result = mutableListOf<Warning>()
        val cond = stmt.condition

        cond.getKnown().forEach {
            if (it !in expressions) {
                newExpressions.add(it)
            }
        }

        cond.getCalculated().forEach {
            result.add(Warning(positionDescription = stmt.getPositionAndDescription(it.print())))
        }

        return result
    }

    private fun Expression.getKnown(): Set<Expression> {
        val result = mutableSetOf<Expression>()

        when (this) {
            is EnclosedExpr -> result.addAll(inner.getKnown())
            is UnaryExpr -> result.add(this)
            is BinaryExpr -> {
                if (operator == BinaryExpr.Operator.AND) {
                    result.add(left)
                    result.add(right)
                    result.addAll(left.getKnown())
                    result.addAll(right.getKnown())
                }
            }
        }

        return result
    }

    private fun Expression.getCalculated(): Set<CalculatedExpression> {
        val result = mutableSetOf<CalculatedExpression>()

        if (this in expressions) {
            result.add(CalculatedExpression(this, true))
        }

        when (this) {
            is EnclosedExpr -> result.addAll(inner.getCalculated())
            is UnaryExpr -> {
                if (operator == UnaryExpr.Operator.LOGICAL_COMPLEMENT) {
                    if (expression in expressions) {
                        result.add(CalculatedExpression(this, false))
                    }

                    result.addAll(expression.getCalculated())
                }
            }
            is BinaryExpr -> {
                val leftValue = left.getCalculated().let { e ->
                    result.addAll(e)
                    e.lastOrNull { it.expression == left }?.value
                }

                val rightValue = right.getCalculated().let { e ->
                    result.addAll(e)
                    e.lastOrNull { it.expression == right }?.value
                }

                when (operator) {
                    BinaryExpr.Operator.AND -> {
                        if (leftValue == true && rightValue == true) {
                            result.add(CalculatedExpression(this, true))
                        }

                        if (leftValue == false || rightValue == false) {
                            result.add(CalculatedExpression(this, false))
                        }
                    }
                    BinaryExpr.Operator.OR -> {
                        if (leftValue == true || rightValue == true) {
                            result.add(CalculatedExpression(this, true))
                        }

                        if (leftValue == false && rightValue == false) {
                            result.add(CalculatedExpression(this, false))
                        }
                    }
                }
            }
        }

        return result
    }
}

private data class CalculatedExpression(
    val expression: Expression,
    val value: Boolean
)

private fun CalculatedExpression.print() = "$expression has the value = $value"