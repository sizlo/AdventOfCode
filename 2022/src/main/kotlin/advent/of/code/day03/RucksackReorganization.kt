package advent.of.code.day03

import advent.of.code.utils.readInputLines
import advent.of.code.utils.splitInHalf
import java.lang.RuntimeException

private fun Char.getPriority(): Int {
    return when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> throw RuntimeException("Cannot get priority of Char: $this")
    }
}

class RucksackReorganization {
    fun getSumOfPrioritiesOfWrongItemInEachRucksack(rucksacks: List<String>): Int {
        return rucksacks
            .map { it.toList() }
            .map { it.splitInHalf() }
            .map { it.first.intersect(it.second) }
            .map { it.first() }
            .sumOf { it.getPriority() }
    }

    fun getSumOfPrioritiesOfGroupBadges(rucksacks: List<String>): Int {
        return rucksacks
            .map { it.toList() }
            .chunked(3)
            .map { it[0].intersect(it[1]).intersect(it[2]) }
            .map { it.first() }
            .sumOf { it.getPriority() }
    }
}

fun main() {
    val input = readInputLines("/day03/input.txt")
    println(RucksackReorganization().getSumOfPrioritiesOfWrongItemInEachRucksack(input)) // 8202
    println(RucksackReorganization().getSumOfPrioritiesOfGroupBadges(input)) // 2864
}