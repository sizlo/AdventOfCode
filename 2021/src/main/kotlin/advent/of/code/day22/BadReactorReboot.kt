package advent.of.code.day22

import advent.of.code.utils.*
import kotlin.system.measureTimeMillis

class BadReactorReboot: ReactorReboot {

    override fun countCubesThatAreOnAfterApplyingRebootSteps(
        input: List<String>,
        filterBounds: Boolean,
        showProgress: Boolean
    ): Long {
        val rebootSteps = input.map { RebootStep.fromString(it) }

        val onRanges = rebootSteps
            .filter { !filterBounds || isWithinBounds(it.cubeRange) }
            .foldIndexed(emptyList<CubeRange>()) { index, onRanges, rebootStep ->
                if (showProgress) {
                    ProgressPrinter().printProgress("On step $index / ${rebootSteps.size}. Current ON ranges = ${onRanges.size}")
                }
                rebootStep.apply(onRanges)
            }

        return onRanges.sumOf { it.getArea() }
    }

    private fun RebootStep.apply(onRanges: List<CubeRange>): List<CubeRange> {
        val onRangesWithOverlapRemoved = onRanges
            .flatMap { it.removeOverlap(this.cubeRange) }

        return when(state) {
            State.ON -> onRangesWithOverlapRemoved + this.cubeRange
            State.OFF -> onRangesWithOverlapRemoved
        }
    }
}

fun main() {
    val input = readInputLines("/day22/input.txt")
    val part1 = BadReactorReboot().countCubesThatAreOnAfterApplyingRebootSteps(input, filterBounds = true)
    println("Part 1 result: $part1\n") // 547648

    requireHeapSpaceInGigabytes(8)

    val timeForPart2 = measureTimeMillis {
        val part2 = BadReactorReboot().countCubesThatAreOnAfterApplyingRebootSteps(input, filterBounds = false, showProgress = true)
        println("\nPart 2 result: $part2\n") // 1206644425246111
    }

    println("Part 2 took ${timeForPart2}ms") // 1207922ms ~= 20 minutes
}