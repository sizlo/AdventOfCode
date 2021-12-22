package advent.of.code.day22

import advent.of.code.utils.readInputLines

class GoodReactorReboot: ReactorReboot {

    data class SignedCubeRange(val cubeRange: CubeRange, val sign: Int)

    override fun countCubesThatAreOnAfterApplyingRebootSteps(
        input: List<String>,
        filterBounds: Boolean,
        showProgress: Boolean
    ): Long {
        val rebootSteps = input.map { RebootStep.fromString(it) }

        val signedCubeRanges = rebootSteps
            .filter { !filterBounds || isWithinBounds(it.cubeRange) }
            .fold(emptyList<SignedCubeRange>()) { signedCubeRanges, rebootStep ->
                rebootStep.apply(signedCubeRanges)
            }

        return signedCubeRanges.sumOf { it.cubeRange.getArea() * it.sign }
    }

    private fun RebootStep.apply(signedCubeRanges: List<SignedCubeRange>): List<SignedCubeRange> {
        val rangesWithNegatedIntersects = signedCubeRanges.flatMap { signedRange ->
            signedRange.cubeRange.getIntersectWith(this.cubeRange)?.let { intersect ->
                listOf(signedRange, SignedCubeRange(intersect, signedRange.sign * -1))
            } ?: listOf(signedRange)
        }

        return when(this.state) {
            State.ON -> rangesWithNegatedIntersects + SignedCubeRange(this.cubeRange, 1)
            State.OFF -> rangesWithNegatedIntersects
        }
    }
}

fun main() {
    val input = readInputLines("/day22/input.txt")
    println(GoodReactorReboot().countCubesThatAreOnAfterApplyingRebootSteps(input, filterBounds = true)) // 547648
    println(GoodReactorReboot().countCubesThatAreOnAfterApplyingRebootSteps(input, filterBounds = false)) // 1206644425246111
}