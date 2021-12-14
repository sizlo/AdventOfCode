package advent.of.code.day14

import advent.of.code.utils.readInput

class ExtendedPolymerization {

    private fun <T> MutableMap<T, Long>.incrementEntryBy(key: T, amount: Long) {
        val oldValue = this[key] ?: 0
        this[key] = oldValue + amount
    }

    fun findMostCommonElementMinusLeastCommonElementAfterNSteps(input: List<String>, steps: Int): Long {
        val polymerTemplate = input[0]
        var pairCounts = polymerTemplate
            .windowed(size = 2, step = 1)
            .groupBy { it }
            .mapValues { it.value.size.toLong() }

        val pairInsertionRules = input
            .subList(2, input.size)
            .map { it.split(" -> ") }
            .associate { it[0] to it[1] }

        repeat(steps) {
            pairCounts = applyRules(pairCounts, pairInsertionRules)
        }

        val elementCounts = getElementCounts(polymerTemplate, pairCounts)

        return elementCounts.maxOf { it.value } - elementCounts.minOf { it.value }
    }

    private fun applyRules(
        pairCounts: Map<String, Long>,
        pairInsertionRules: Map<String, String>
    ): Map<String, Long> {
        val nextPairCounts = pairCounts.toMutableMap()

        pairCounts
            .forEach { (pair, count) ->
                pairInsertionRules[pair]?.let { elementToInsert ->
                    nextPairCounts.incrementEntryBy(pair, -count)
                    nextPairCounts.incrementEntryBy("${pair[0]}${elementToInsert}", count)
                    nextPairCounts.incrementEntryBy("${elementToInsert}${pair[1]}", count)
                }
            }

        return nextPairCounts
    }

    private fun getElementCounts(polymerTemplate: String, pairCounts: Map<String, Long>): Map<Char, Long> {
        val doubledElementCounts = mutableMapOf<Char, Long>()

        pairCounts.forEach { (pair, count) ->
            doubledElementCounts.incrementEntryBy(pair[0], count)
            doubledElementCounts.incrementEntryBy(pair[1], count)
        }

        // The first and last element only appear in one pair, so have not been double counted
        doubledElementCounts.incrementEntryBy(polymerTemplate.first(), 1)
        doubledElementCounts.incrementEntryBy(polymerTemplate.last(), 1)

        return doubledElementCounts.mapValues { it.value / 2 }
    }
}

fun main() {
    val input = readInput("/day14/input.txt")
    println(ExtendedPolymerization().findMostCommonElementMinusLeastCommonElementAfterNSteps(input, steps = 10)) // 2509
    println(ExtendedPolymerization().findMostCommonElementMinusLeastCommonElementAfterNSteps(input, steps = 40)) // 2827627697643
}