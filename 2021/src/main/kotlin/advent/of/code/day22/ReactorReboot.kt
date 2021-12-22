package advent.of.code.day22

import advent.of.code.day22.ReactorReboot.State.*
import advent.of.code.utils.containsOther
import advent.of.code.utils.readInputLines
import advent.of.code.utils.splitBasedOnOverlapWith
import advent.of.code.utils.toIntList
import java.lang.RuntimeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.measureTimeMillis

class ReactorReboot {

    enum class State { ON, OFF }

    data class CubeRange(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
        fun getArea() = xRange.count().toLong() * yRange.count().toLong() * zRange.count().toLong()

        fun removeOverlap(other: CubeRange): List<CubeRange> {
            val allCubeRanges = xRange.splitBasedOnOverlapWith(other.xRange).flatMap { xRange ->
                yRange.splitBasedOnOverlapWith(other.yRange).flatMap { yRange ->
                    zRange.splitBasedOnOverlapWith(other.zRange).map { zRange ->
                        CubeRange(xRange, yRange, zRange)
                    }
                }
            }
            return allCubeRanges.filter { !other.contains(it) }
        }

        private fun contains(other: CubeRange): Boolean {
            return this.xRange.containsOther(other.xRange)
                    && this.yRange.containsOther(other.yRange)
                    && this.zRange.containsOther(other.zRange)
        }
    }

    data class RebootStep(val state: State, val cubeRange: CubeRange) {
        companion object {
            fun fromString(string: String): RebootStep {
                val state = if (string.startsWith("on")) ON else OFF
                val ranges = string.split(" ")[1].split(",")
                val xRange = rangeFromString(ranges[0])
                val yRange = rangeFromString(ranges[1])
                val zRange = rangeFromString(ranges[2])
                return RebootStep(state, CubeRange(xRange, yRange, zRange))
            }

            private fun rangeFromString(rangeString: String): IntRange {
                val endpoints = rangeString.substring(2).split("..").toIntList()
                return endpoints[0] .. endpoints[1]
            }
        }

        fun apply(onRanges: List<CubeRange>): List<CubeRange> {
            val onRangesWithOverlapRemoved = onRanges
                .flatMap { it.removeOverlap(this.cubeRange) }

            return when(state) {
                ON -> onRangesWithOverlapRemoved + this.cubeRange
                OFF -> onRangesWithOverlapRemoved
            }
        }
    }

    fun countCubesThatAreOnAfterApplyingRebootSteps(
        input: List<String>,
        filterBounds: Boolean,
        showProgress: Boolean = false
    ): Long {
        val rebootSteps = input.map { RebootStep.fromString(it) }

        val onRanges = rebootSteps
            .filter { !filterBounds || isWithinBounds(it.cubeRange) }
            .foldIndexed(emptyList<CubeRange>()) { index, onRanges, rebootStep ->
                if (showProgress) {
                    println("${LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)} - Progress: On step $index / ${rebootSteps.size}. Current ON ranges = ${onRanges.size}")
                }
                rebootStep.apply(onRanges)
            }

        return onRanges.sumOf { it.getArea() }
    }

    private fun isWithinBounds(cubeRange: CubeRange): Boolean {
        val boundsRange = -50 .. 50
        return boundsRange.containsOther(cubeRange.xRange)
                && boundsRange.containsOther(cubeRange.yRange)
                && boundsRange.containsOther(cubeRange.zRange)
    }
}

fun main() {
    val input = readInputLines("/day22/input.txt")
    val part1 = ReactorReboot().countCubesThatAreOnAfterApplyingRebootSteps(input, filterBounds = true)
    println("Part 1 result: $part1\n") // 547648

    // This is disgusting
    if (Runtime.getRuntime().maxMemory() < 8589934592) {
        throw RuntimeException("The part 2 solution is Not Good™ and requires at least 8GB of heap space")
    }

    val timeForPart2 = measureTimeMillis {
        val part2 = ReactorReboot().countCubesThatAreOnAfterApplyingRebootSteps(input, filterBounds = false, showProgress = true)
        println("\nPart 2 result: $part2\n") // 1206644425246111
    }

    println("Part 2 took ${timeForPart2}ms") // 1207922ms ~= 20 minutes
}