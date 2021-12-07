package advent.of.code.day07

import advent.of.code.utils.Part
import advent.of.code.utils.Part.PART_1
import advent.of.code.utils.Part.PART_2
import advent.of.code.utils.readInputAsOneListOfIntegers
import kotlin.math.absoluteValue

class TreacheryOfWhales(part: Part) {

    private val fuelUsageCalculator = if (part == PART_1) LinearFuelUsageCalculator() else ExponentialFuelUsageCalculator()

    abstract class FuelUsageCalculator {
        abstract fun calculateFuelNeededToAlignToPosition(crabPositions: List<Int>, target: Int): Int

        protected fun getDistanceBetween(crabPosition: Int, target: Int): Int {
            return (target - crabPosition).absoluteValue
        }
    }

    class LinearFuelUsageCalculator: FuelUsageCalculator() {
        override fun calculateFuelNeededToAlignToPosition(crabPositions: List<Int>, target: Int): Int {
            return crabPositions.sumOf { getDistanceBetween(it, target) }
        }
    }

    class ExponentialFuelUsageCalculator: FuelUsageCalculator() {
        override fun calculateFuelNeededToAlignToPosition(crabPositions: List<Int>, target: Int): Int {
            return crabPositions
                .map { getDistanceBetween(it, target) }
                .sumOf { calculateTriangleNumberForDistance(it) }
        }

        private fun calculateTriangleNumberForDistance(distance: Int): Int {
            return (distance * (distance + 1)) / 2
        }
    }

    fun findMinimumFuelNeededToAlign(crabPositions: List<Int>): Int {
        val minStartingPosition = crabPositions.minOf { it }
        val maxStartingPosition = crabPositions.maxOf { it }

        return (minStartingPosition..maxStartingPosition)
            .map { fuelUsageCalculator.calculateFuelNeededToAlignToPosition(crabPositions, it) }
            .minOf { it }
    }
}

fun main() {
    val input = readInputAsOneListOfIntegers("/day07/input.txt")

    val treacheryOfWhalesForPart1 = TreacheryOfWhales(PART_1)
    val treacheryOfWhalesForPart2 = TreacheryOfWhales(PART_2)

    println(treacheryOfWhalesForPart1.findMinimumFuelNeededToAlign(input)) // 355150
    println(treacheryOfWhalesForPart2.findMinimumFuelNeededToAlign(input)) // 98368490
}