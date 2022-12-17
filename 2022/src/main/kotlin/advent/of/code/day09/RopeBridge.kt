package advent.of.code.day09

import advent.of.code.utils.Coordinate
import advent.of.code.utils.readInputLines
import java.lang.RuntimeException
import kotlin.math.absoluteValue

class Knot {
    var position = Coordinate(0, 0)

    fun move(direction: String) {
        position = when(direction) {
            "U" -> position.copy(y = position.y + 1)
            "D" -> position.copy(y = position.y - 1)
            "L" -> position.copy(x = position.x - 1)
            "R" -> position.copy(x = position.x + 1)
            else -> throw RuntimeException("Unknown direction: $direction")
        }
    }

    fun catchUp(followPosition: Coordinate) {
        if (touching(followPosition)) return

        val xStep = getStep(followPosition.x - position.x)
        val yStep = getStep(followPosition.y - position.y)
        position = Coordinate(position.x + xStep, position.y + yStep)
    }

    private fun touching(otherPosition: Coordinate): Boolean {
        return (otherPosition.x - position.x).absoluteValue <= 1
                && (otherPosition.y - position.y).absoluteValue <= 1
    }

    private fun getStep(distance: Int): Int {
        if (distance == 0) return 0
        return distance / distance.absoluteValue
    }
}

class RopeBridge(numKnots: Int) {

    private val rope = List(numKnots) { Knot() }
    private val allPositionsTailHasVisited = mutableSetOf(rope.last().position)

    fun countAllPositionsTailVisits(moveInstructions: List<String>): Int {
        moveInstructions.forEach { applyMoveInstruction(it) }
        return allPositionsTailHasVisited.size
    }

    private fun applyMoveInstruction(moveInstruction: String) {
        val (direction, times) = moveInstruction.split(" ")
        repeat(times.toInt()) {
            rope.first().move(direction)
            rope.zipWithNext().forEach { it.second.catchUp(it.first.position) }
            allPositionsTailHasVisited.add(rope.last().position)
        }
    }
}

fun main() {
    val input = readInputLines("/day09/input.txt")
    println(RopeBridge(2).countAllPositionsTailVisits(input)) // 6209
    println(RopeBridge(10).countAllPositionsTailVisits(input)) // 2460
}
