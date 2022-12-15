package advent.of.code.day04

import advent.of.code.utils.fullyContains
import advent.of.code.utils.overlaps
import advent.of.code.utils.readInputLines
import advent.of.code.utils.toIntRange

class CampCleanup {

    fun countPairsWhereOneRangeFullyContainsAnother(input: List<String>): Int {
        return input
            .map { toRangePair(it) }
            .count { it.first.fullyContains(it.second) || it.second.fullyContains(it.first) }
    }

    fun countPairsWhereRangesOverlap(input: List<String>): Int {
        return input
            .map { toRangePair(it) }
            .count { it.first.overlaps(it.second) }
    }

    private fun toRangePair(input: String): Pair<IntRange, IntRange> {
        val ranges = input
            .split(",")
            .map { it.toIntRange() }
        return Pair(ranges[0], ranges[1])
    }
}

fun main() {
    val input = readInputLines("/day04/input.txt")
    println(CampCleanup().countPairsWhereOneRangeFullyContainsAnother(input)) // 602
    println(CampCleanup().countPairsWhereRangesOverlap(input)) // 891
}