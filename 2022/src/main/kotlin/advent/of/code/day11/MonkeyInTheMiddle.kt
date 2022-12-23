package advent.of.code.day11

import advent.of.code.utils.Part
import advent.of.code.utils.productOf
import advent.of.code.utils.readInput

typealias MonkeyOperation = (Long) -> Long

data class Monkey(
    val items: MutableList<Long>,
    val operation: MonkeyOperation,
    val testDivisor: Long,
    val throwToIfTestPasses: Int,
    val throwToIfTestFails: Int
) {
    var totalInspections = 0L

    companion object {
        fun fromString(input: String): Monkey {
            val lines = input.lines()

            return Monkey(
                getItems(lines[1]),
                getOperation(lines[2]),
                getTestDivisor(lines[3]),
                getThrowTo(lines[4]),
                getThrowTo(lines[5]),
            )
        }

        private fun getItems(input: String) = input.split(": ").last().split(", ").map { it.toLong() }.toMutableList()
        private fun getTestDivisor(input: String) = input.split(" ").last().toLong()
        private fun getThrowTo(input: String) = input.split(" ").last().toInt()

        private fun getOperation(input: String): MonkeyOperation {
            val operationParts = input.split(" ").toMutableList()
            val operand = operationParts.removeLast()
            val operator = operationParts.removeLast()
            return { old: Long ->
                val operandValue = when(operand) {
                    "old" -> old
                    else -> operand.toLong()
                }

                when(operator) {
                    "+" -> old + operandValue
                    "*" -> old * operandValue
                    else -> throw RuntimeException("Unknown operator: $operator")
                }
            }
        }
    }
}

class MonkeyInTheMiddle(input: String, private val part: Part) {
    private val monkeys = input.split("\n\n").map { Monkey.fromString(it) }
    private val commonMultiple = monkeys.productOf { it.testDivisor }

    fun getMonkeyBusinessLevelAfterPlayingGame(): Long {
        val rounds = if (part == Part.PART_1) 20 else 10000

        repeat(rounds) { performRound() }

        return monkeys
            .map { it.totalInspections }
            .sortedDescending()
            .take(2)
            .productOf { it }
    }

    private fun performRound() {
        monkeys.forEach { takeTurn(it) }
    }

    private fun takeTurn(currentMonkey: Monkey) {
        currentMonkey.items.forEach { oldWorryLevel ->
            currentMonkey.totalInspections++

            val worryLevelAfterOperation = currentMonkey.operation(oldWorryLevel)
            val worryLevelAfterAdjustment = when(part) {
                Part.PART_1 -> worryLevelAfterOperation / 3
                Part.PART_2 -> worryLevelAfterOperation % commonMultiple
            }

            val throwTo = when(worryLevelAfterAdjustment % currentMonkey.testDivisor == 0L) {
                true -> currentMonkey.throwToIfTestPasses
                false -> currentMonkey.throwToIfTestFails
            }
            monkeys[throwTo].items.add(worryLevelAfterAdjustment)
        }

        currentMonkey.items.removeAll { true }
    }
}

fun main() {
    val input = readInput("/day11/input.txt")
    println(MonkeyInTheMiddle(input, Part.PART_1).getMonkeyBusinessLevelAfterPlayingGame()) // 62491
    println(MonkeyInTheMiddle(input, Part.PART_2).getMonkeyBusinessLevelAfterPlayingGame()) // 17408399184
}
