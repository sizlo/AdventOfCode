package advent.of.code.day14

import advent.of.code.utils.Coordinate
import advent.of.code.utils.Part
import advent.of.code.utils.readInputLines
import kotlin.math.sign

class RegolithReservoir(rockStructures: List<String>, val part: Part) {

    enum class Material { ROCK, SAND }

    private val map = rockStructures.flatMap { parseRockStructure(it) }.associateWith { Material.ROCK }.toMutableMap()
    private val lowestRock = map.keys.maxOf { it.y }
    private val floor = lowestRock + 2
    private val source = Coordinate(500, 0)

    private fun Map<Coordinate, Material>.debugString(): String {
        val minX = this.keys.minOf { it.x }
        val minY = 0
        val maxX = this.keys.maxOf { it.x }
        val maxY = floor

        return (minY..maxY).joinToString("\n") { y ->
            (minX..maxX).joinToString("") { x ->
                val coordinate = Coordinate(x, y)
                when {
                    coordinate == source -> "+"
                    this[coordinate] == Material.ROCK -> "#"
                    part == Part.PART_2 && coordinate.y == floor -> "#"
                    this[coordinate] == Material.SAND -> "o"
                    else -> "."
                }
            }
        }
    }

    fun countUnitsOfSandWhichComeToRest(): Int {
        while(simulateSand()) {
//            println("After ${map.count { it.value == Material.SAND }}")
//            println("${map.debugString()}\n")
        }
        return map.count { it.value == Material.SAND }
    }

    private fun parseRockStructure(rockStructure: String): List<Coordinate> {
        return rockStructure
            .split(" -> ")
            .map { Coordinate(it) }
            .zipWithNext()
            .flatMap { interpolate(it.first, it.second) }
    }

    private fun interpolate(start: Coordinate, end: Coordinate): List<Coordinate> {
        val xStep = (end.x - start.x).sign
        val yStep = (end.y - start.y).sign

        val result = mutableListOf(start)
        var current = start.copy()

        do {
            current = Coordinate(current.x + xStep, current.y + yStep)
            result.add(current)
        } while (current != end)

        return result
    }

    private fun simulateSand(): Boolean {
        if (!isPositionEmpty(source)) return false

        var position = source.copy()
        while(true) {
            if (part == Part.PART_1 && position.y > lowestRock) return false

            val down = position.down()
            val downLeft = down.left()
            val downRight = down.right()

            position = when {
                isPositionEmpty(down) -> down
                isPositionEmpty(downLeft) -> downLeft
                isPositionEmpty(downRight) -> downRight
                else -> {
                    map[position] = Material.SAND
                    return true
                }
            }
        }
    }

    private fun isPositionEmpty(position: Coordinate): Boolean {
        if (position.y == floor) return false
        return !map.contains(position)
    }

    private fun Coordinate.down() = copy(y = y + 1)
    private fun Coordinate.left() = copy(x = x - 1)
    private fun Coordinate.right() = copy(x = x + 1)
}

fun main() {
    val input = readInputLines("/day14/input.txt")
    println(RegolithReservoir(input, Part.PART_1).countUnitsOfSandWhichComeToRest()) // 692
    println(RegolithReservoir(input, Part.PART_2).countUnitsOfSandWhichComeToRest()) // 31706
}
