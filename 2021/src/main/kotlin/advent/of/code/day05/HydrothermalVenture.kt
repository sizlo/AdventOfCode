package advent.of.code.day05

import advent.of.code.utils.Coordinate
import advent.of.code.utils.readInput
import kotlin.math.abs

class HydrothermalVenture(input: List<String>) {

    private val lines = input.map(::parseLine)

    class Line(private val endpoints: Pair<Coordinate, Coordinate>) {

        fun getCoveredCoordinates(): List<Coordinate> {
            val dx = calculateDelta(endpoints.first.x, endpoints.second.x)
            val dy = calculateDelta(endpoints.first.y, endpoints.second.y)

            var x = endpoints.first.x
            var y = endpoints.first.y

            val coordinates = mutableListOf<Coordinate>()

            while (!coordinates.contains(endpoints.second)) {
                coordinates.add(Coordinate(x, y))
                x += dx
                y += dy
            }

            return coordinates
        }

        private fun calculateDelta(first: Int, second: Int): Int {
            if (first == second) return 0
            val difference = second - first
            return difference / abs(difference)
        }

        fun isHorizontal(): Boolean = endpoints.first.y == endpoints.second.y
        fun isVertical(): Boolean = endpoints.first.x == endpoints.second.x
    }

    fun countPointsCoveredByNonDiagonalLines(minimumOverlap: Int): Int {
        val nonDiagonalLines = lines.filter { it.isHorizontal() || it.isVertical() }
        return countPointsCoveredByLines(nonDiagonalLines, minimumOverlap)
    }

    fun countPointsCoveredByLines(minimumOverlap: Int): Int {
        return countPointsCoveredByLines(lines, minimumOverlap)
    }

    private fun countPointsCoveredByLines(lines: List<Line>, minimumOverlap: Int): Int {
        return lines
            .flatMap(Line::getCoveredCoordinates)
            .groupBy { it }
            .count { it.value.size >= minimumOverlap }
    }

    private fun parseLine(string: String): Line {
        val coordinates = string
            .split(" -> ")
            .map { Coordinate(it) }
        return Line(Pair(coordinates[0], coordinates[1]))
    }
}

fun main() {
    val input = readInput("/day05/input.txt")
    val hydrothermalVenture = HydrothermalVenture(input)
    println(hydrothermalVenture.countPointsCoveredByNonDiagonalLines(minimumOverlap = 2)) // 5698
    println(hydrothermalVenture.countPointsCoveredByLines(minimumOverlap = 2)) // 15463
}