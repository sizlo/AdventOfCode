package advent.of.code.day01

import advent.of.code.utils.readInputLines
import advent.of.code.utils.splitOnBlankLines
import advent.of.code.utils.toIntList

typealias ElfInventory = List<Int>

class CalorieCounting {

    fun findLargestTotalCaloriesInASingleElfInventory(elfInventories: List<ElfInventory>): Int {
        return findLargestTotalCaloriesInNElvesInventories(elfInventories, 1)
    }

    fun findLargestTotalCaloriesInThreeElvesInventories(elfInventories: List<ElfInventory>): Int {
        return findLargestTotalCaloriesInNElvesInventories(elfInventories, 3)
    }

    private fun findLargestTotalCaloriesInNElvesInventories(elfInventories: List<ElfInventory>, n: Int): Int {
        return elfInventories
            .map { it.sum() }
            .sortedDescending()
            .take(n)
            .sum()
    }
}

fun main() {
    val input = readInputLines("/day01/input.txt")
        .splitOnBlankLines()
        .map { it.toIntList() }
    println(CalorieCounting().findLargestTotalCaloriesInASingleElfInventory(input)) // 68802
    println(CalorieCounting().findLargestTotalCaloriesInThreeElvesInventories(input)) // 205370
}
