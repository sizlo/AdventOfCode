package advent.of.code.day01

import advent.of.code.utils.readInputLines
import advent.of.code.utils.toIntList

class SonarSweep {
    fun part1(seaFloorDepths: List<Int>): Int {
        return countIncrements(seaFloorDepths)
    }

    fun part2(seaFloorDepths: List<Int>): Int {
        val depthMeasurementWindows = seaFloorDepths.windowed(3, 1, transform = List<Int>::sum)
        return countIncrements(depthMeasurementWindows)
    }

    private fun countIncrements(intList: List<Int>): Int {
        return intList
                .zipWithNext()
                .count { (previousDepth, currentDepth) -> currentDepth > previousDepth }
    }
}

fun main() {
    val input = readInputLines("/day01/input.txt").toIntList()
    println(SonarSweep().part1(input)) // 1583
    println(SonarSweep().part2(input)) // 1627
}