package advent.of.code.day05

import advent.of.code.utils.Part
import advent.of.code.utils.readInputLines
import advent.of.code.utils.splitOnBlankLines

typealias CrateStack = MutableList<Char>
private fun emptyCrateStack() = mutableListOf<Char>()
private fun CrateStack.removeLast(n: Int): List<Char> {
    val result = this.slice(IntRange(this.size - n, this.size - 1))
    repeat(n) { this.removeLast() }
    return result
}

class SupplyStacks {

    data class Move(val numCrates: Int, val from: Int, val to: Int) {
        companion object {
            fun fromString(input: String): Move {
                val match = """move (\d+) from (\d+) to (\d+)""".toRegex().find(input)!!
                return Move(
                    match.groupValues[1].toInt(),
                    match.groupValues[2].toInt(),
                    match.groupValues[3].toInt(),
                )
            }
        }
    }

    fun findTopCratesAfterRearrangementAsAString(input: List<String>, part: Part): String {
        val (stacksInput, movesInput) = input.splitOnBlankLines()
        val stacks = parseStacks(stacksInput)
        val moves = movesInput.map { Move.fromString(it) }
        moves.forEach { performMove(it, stacks, part) }
        return getAllTopCratesAsString(stacks)
    }

    private fun parseStacks(stacksInput: List<String>): Map<Int, CrateStack> {
        val reversedInput = stacksInput.reversed()
        val names = reversedInput.first()
        val crates = reversedInput.subList(1, reversedInput.size)

        val result = mutableMapOf<Int, CrateStack>()

        names.toList()
            .forEachIndexed { index, name ->
                if (name in '1'..'9') {
                    val stack = emptyCrateStack()
                    crates
                        .mapNotNull { it.getOrNull(index) }
                        .filterNot { it.isWhitespace() }
                        .forEach { stack.add(it) }

                    result[name.digitToInt()] = stack
                }
            }

        return result
    }

    private fun performMove(move: Move, stacks: Map<Int, CrateStack>, part: Part) {
        val fromStack = stacks[move.from]!!
        val toStack = stacks[move.to]!!

        val part1Move = {
            repeat(move.numCrates) {
                toStack.add(fromStack.removeLast())
            }
        }

        val part2Move = {
            toStack.addAll(fromStack.removeLast(move.numCrates))
        }

        when (part) {
            Part.PART_1 -> part1Move()
            Part.PART_2 -> part2Move()
        }
    }

    private fun getAllTopCratesAsString(stacks: Map<Int, CrateStack>): String {
        return stacks.values.map { it.last() }.joinToString("")
    }
}

fun main() {
    val input = readInputLines("/day05/input.txt")
    println(SupplyStacks().findTopCratesAfterRearrangementAsAString(input, Part.PART_1)) // QMBMJDFTD
    println(SupplyStacks().findTopCratesAfterRearrangementAsAString(input, Part.PART_2)) // NBTVTJNFJ
}
