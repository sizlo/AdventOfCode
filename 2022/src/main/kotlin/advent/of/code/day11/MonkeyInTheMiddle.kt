package advent.of.code.day11

import advent.of.code.utils.Part
import advent.of.code.utils.productOf
import advent.of.code.utils.readInput

typealias MonkeyOperation = (Long) -> Long
typealias MonkeyTest = (Long) -> Boolean

data class Monkey(
    val items: MutableList<Long>,
    val operation: MonkeyOperation,
    val test: MonkeyTest,
    val throwToIfTestPasses: Int,
    val throwToIfTestFails: Int
) {
    var totalInspections = 0L

    companion object {
        fun fromString(input: String): Monkey {
            val lines = input.split("\n")

            return Monkey(
                getItems(lines[1]),
                getOperation(lines[2]),
                getTest(lines[3]),
                getThrowTo(lines[4]),
                getThrowTo(lines[5]),
            )
        }

        private fun getItems(input: String) = input.split(": ").last().split(", ").map { it.toLong() }.toMutableList()

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

        private fun getTest(input: String): MonkeyTest {
            if (!input.contains("divisible by")) throw RuntimeException("Unknown test type: $input")
            val divisor = input.split(" ").last().toLong()
            return { value: Long -> value % divisor == 0L }
        }

        private fun getThrowTo(input: String) = input.split(" ").last().toInt()
    }
}

class MonkeyInTheMiddle(input: String) {
    private val monkeys = input.split("\n\n").map { Monkey.fromString(it) }

    fun getMonkeyBusinessLevelAfterPlayingGame(part: Part): Long {
        val (rounds, worryDivisor) = when(part) {
            Part.PART_1 -> Pair(20, 3L)
            Part.PART_2 -> Pair(10000, 1L)
        }

        repeat(rounds) { performRound(worryDivisor) }

        return monkeys
            .map { it.totalInspections }
            .sortedDescending()
            .take(2)
            .productOf { it }
    }

    private fun performRound(worryDivisor: Long) {
        monkeys.forEach { takeTurn(it, worryDivisor) }
    }

    private fun takeTurn(currentMonkey: Monkey, worryDivisor: Long) {
        currentMonkey.items.forEach { oldWorryLevel ->
            currentMonkey.totalInspections++
            val newWorryLevel = currentMonkey.operation(oldWorryLevel) / worryDivisor
            val throwTo = when(currentMonkey.test(newWorryLevel)) {
                true -> currentMonkey.throwToIfTestPasses
                false -> currentMonkey.throwToIfTestFails
            }
            monkeys[throwTo].items.add(newWorryLevel)
        }

        currentMonkey.items.removeAll { true }
    }
}

fun main() {
    val input = readInput("/day11/input.txt")
    println(MonkeyInTheMiddle(input).getMonkeyBusinessLevelAfterPlayingGame(Part.PART_1)) // 62491
    TODO("Getting overflows of the worry level values when running for all the rounds required in part 2")
    println(MonkeyInTheMiddle(input).getMonkeyBusinessLevelAfterPlayingGame(Part.PART_2)) //
}
