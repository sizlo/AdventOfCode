package advent.of.code.day17

import advent.of.code.utils.Coordinate
import advent.of.code.utils.Velocity
import advent.of.code.utils.readInput
import kotlin.math.max

class TrickShot(input: String) {

    class Area(val xRange: IntRange, val yRange: IntRange) {

        fun contains(coordinate: Coordinate): Boolean {
            return xRange.contains(coordinate.x) && yRange.contains(coordinate.y)
        }

        fun hasBeenPassed(coordinate: Coordinate): Boolean {
            // Relies on assumption that area is always below and to the right of launcher
            return coordinate.x > xRange.last || coordinate.y < yRange.first
        }
    }

    data class LaunchResult(val hitTarget: Boolean, val highestYPosition: Int)

    class Launcher(private val target: Area) {
        fun launch(initialVelocity: Velocity): LaunchResult {
            var position = Coordinate(0, 0)
            var velocity = initialVelocity
            var maxY = position.y

            while (!target.contains(position) && !target.hasBeenPassed(position)) {
                position = Coordinate(position.x + velocity.x, position.y + velocity.y)
                velocity = applyDrag(velocity)
                velocity = applyGravity(velocity)
                maxY = max(position.y, maxY)
            }

            return LaunchResult(target.contains(position), maxY)
        }

        private fun applyDrag(velocity: Velocity): Velocity {
            val newX = when {
                velocity.x > 0 -> velocity.x - 1
                velocity.x < 0 -> velocity.x + 1
                else -> 0
            }
            return velocity.copy(x = newX)
        }

        private fun applyGravity(velocity: Velocity): Velocity = velocity.copy(y = velocity.y - 1)
    }

    private val target: Area

    init {
        val ranges = input
            .removePrefix("target area: ")
            .split(", ")
            .map { range ->
                val withoutAxis = range.substring(2)
                val minAndMax = withoutAxis.split("..").map { it.toInt() }
                minAndMax[0] .. minAndMax[1]
            }
        target = Area(ranges[0], ranges[1])
    }

    fun findHighestPossibleYPositionProbeCanReachAndStillHitTargetArea(): Int {
        val launcher = Launcher(target)
        val xVelocity = findXVelocityWhichStopsWithinTargetDueToDrag(target.xRange)

        var yVelocity = Short.MAX_VALUE.toInt()
        while (true) {
            val result = launcher.launch(Velocity(xVelocity, yVelocity))

            if (result.hitTarget) {
                return result.highestYPosition
            }

            yVelocity--
        }
    }

    private fun findXVelocityWhichStopsWithinTargetDueToDrag(xRange: IntRange): Int {
        var velocity = 0
        var positions = xRange.toList()

        while (!positions.contains(0)) {
            velocity++
            positions = positions.map { it - velocity }
        }

        return velocity
    }
}

fun main() {
    val input = readInput("/day17/input.txt")
    println(TrickShot(input).findHighestPossibleYPositionProbeCanReachAndStillHitTargetArea()) // 9180
}