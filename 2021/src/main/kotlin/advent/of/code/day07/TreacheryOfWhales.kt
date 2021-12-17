package advent.of.code.day07

import advent.of.code.utils.Part
import advent.of.code.utils.Part.PART_1
import advent.of.code.utils.Part.PART_2
import advent.of.code.utils.readInputAsOneListOfIntegers
import advent.of.code.utils.triangle
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
                .sumOf { it.triangle() }
        }
    }

    fun findMinimumFuelNeededToAlign(crabPositions: List<Int>): Int {
        val minStartingPosition = crabPositions.minOf { it }
        val maxStartingPosition = crabPositions.maxOf { it }

        return (minStartingPosition..maxStartingPosition)
            .minOf { fuelUsageCalculator.calculateFuelNeededToAlignToPosition(crabPositions, it) }
    }
}

fun main() {
    val input = readInputAsOneListOfIntegers("/day07/input.txt")
    println(TreacheryOfWhales(PART_1).findMinimumFuelNeededToAlign(input)) // 355150
    println(TreacheryOfWhales(PART_2).findMinimumFuelNeededToAlign(input)) // 98368490
}