package advent.of.code.day17

import advent.of.code.utils.*
import kotlin.math.absoluteValue
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


    class Launcher(private val target: Area) {
        fun hitsTargetWithInitialVelocity(initialVelocity: Velocity): Boolean {
            var position = Coordinate(0, 0)
            var velocity = initialVelocity
            var maxY = position.y

            while (!target.contains(position) && !target.hasBeenPassed(position)) {
                position = Coordinate(position.x + velocity.x, position.y + velocity.y)
                velocity = applyDrag(velocity)
                velocity = applyGravity(velocity)
                maxY = max(position.y, maxY)
            }

            return target.contains(position)
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
        val yVelocity = findHighestYVelocityThatPassesThroughTarget(target.yRange)
        return yVelocity.triangle()
    }

    fun findNumberOfInitialVelocitiesWhereProbeHitsTarget(): Int {
        val launcher = Launcher(target)

        val minYVelocity = target.yRange.minOf { it }
        val maxYVelocity = findHighestYVelocityThatPassesThroughTarget(target.yRange)

        val minXVelocity = findLowestXVelocityWhichStopsWithinTargetDueToDrag(target.xRange)
        val maxXVelocity = target.xRange.maxOf { it }

        val velocitiesToCheck = (minYVelocity .. maxYVelocity)
            .flatMap { y ->
                (minXVelocity .. maxXVelocity).map { x ->
                    Velocity(x, y)
                }
            }

        return velocitiesToCheck.count { launcher.hitsTargetWithInitialVelocity(it) }
    }

    private fun findHighestYVelocityThatPassesThroughTarget(target: IntRange): Int {
        // Any probe launched with a positive y velocity=V will go up, then come back down,
        //   passing through y=0 with a y velocity=-V
        // The highest this can be and still pass through the target is if on the next step
        //   (where it has y velocity=-(V+1) it hit the bottom of the target
        // So the max y velocity is distanceToBottomOfTarget - 1
        return target.minOf { it }.absoluteValue - 1
    }

    private fun findLowestXVelocityWhichStopsWithinTargetDueToDrag(target: IntRange): Int {
        var velocity = 0
        var position = 0

        while (!target.contains(position)) {
            velocity++
            position+= velocity
        }

        return velocity
    }
}

fun main() {
    val input = readInput("/day17/input.txt")
    val trickShot = TrickShot(input)
    println(trickShot.findHighestPossibleYPositionProbeCanReachAndStillHitTargetArea()) // 9180
    println(trickShot.findNumberOfInitialVelocitiesWhereProbeHitsTarget()) // 3767
}