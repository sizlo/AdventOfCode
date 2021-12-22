package advent.of.code.day22

import advent.of.code.utils.containsOther
import advent.of.code.utils.getIntersectWithOther
import advent.of.code.utils.splitBasedOnOverlapWith
import advent.of.code.utils.toIntList

interface ReactorReboot {
    fun countCubesThatAreOnAfterApplyingRebootSteps(
        input: List<String>,
        filterBounds: Boolean,
        showProgress: Boolean = false
    ): Long

    fun isWithinBounds(cubeRange: CubeRange): Boolean {
        val boundsRange = -50 .. 50
        return boundsRange.containsOther(cubeRange.xRange)
                && boundsRange.containsOther(cubeRange.yRange)
                && boundsRange.containsOther(cubeRange.zRange)
    }
}

enum class State { ON, OFF }

data class CubeRange(val xRange: IntRange, val yRange: IntRange, val zRange: IntRange) {
    fun getArea() = xRange.count().toLong() * yRange.count().toLong() * zRange.count().toLong()

    fun getIntersectWith(other: CubeRange): CubeRange? {
        val intersectX = this.xRange.getIntersectWithOther(other.xRange)
        val intersectY = this.yRange.getIntersectWithOther(other.yRange)
        val intersectZ = this.zRange.getIntersectWithOther(other.zRange)
        return if (intersectX == null || intersectY == null || intersectZ == null) {
            null
        } else {
            CubeRange(intersectX, intersectY, intersectZ)
        }
    }

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
            val state = if (string.startsWith("on")) State.ON else State.OFF
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
}