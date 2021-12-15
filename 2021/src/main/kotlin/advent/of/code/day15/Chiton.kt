package advent.of.code.day15

import advent.of.code.utils.*
import advent.of.code.utils.Part.PART_1
import advent.of.code.utils.Part.PART_2
import java.util.*
import kotlin.system.measureTimeMillis

typealias Tile = List<List<Int>>

fun Tile.incrementTile(): Tile {
    return this
        .map { row ->
            row.map { if (it + 1 > 9) 1 else it + 1 }
        }
}

fun Tile.joinRight(rhs: Tile): Tile {
    return this.zip(rhs) { left, right -> left + right }
}

fun Tile.joinBottom(btm: Tile): Tile {
    return this + btm
}

class Chiton(private val part: Part) {

    class RiskLevelMap(input: List<String>): Grid<Int>(width = input.first().length, height = input.size) {

        init {
            input.forEach { row ->
                row.forEach { items.add(it.digitToInt()) }
            }
        }

        fun findLowestPathRisk(): Int {
            val allCoordinates = (0 until height)
                .flatMap { y ->
                    (0 until width).map { x ->
                        Coordinate(x, y)
                    }
                }

            val start = Coordinate(0, 0)
            val end = Coordinate(width - 1, height - 1)

            val distances = allCoordinates.associateWith { Int.MAX_VALUE }.toMutableMap()
            val unvisited = PriorityQueue<Coordinate> { a, b -> distances[a]!! - distances[b]!! }
            allCoordinates.forEach { unvisited.add(it) }

            distances[start] = 0

            while (unvisited.contains(end)) {
                val current = unvisited.poll()!!

                current
                    .getNeighbourCoordinates()
                    .filter { unvisited.contains(it) }
                    .forEach { neighbour ->
                        val distanceToCurrent = getItemAt(neighbour)!! + distances[current]!!
                        if (distanceToCurrent < distances[neighbour]!!) {
                            distances[neighbour] = distanceToCurrent

                            // Adjust-priority
                            unvisited.remove(neighbour)
                            unvisited.add(neighbour)
                        }
                    }
            }

            return distances[end]!!
        }
    }



    fun findLowestPathRisk(input: List<String>): Int {
        return when (part) {
            PART_1 -> RiskLevelMap(input).findLowestPathRisk()
            PART_2 -> RiskLevelMap(generateTiles(input)).findLowestPathRisk()
        }
    }

    private fun generateTiles(input: List<String>): List<String> {
        val tile = input
            .map { row ->
                row.toList().map { item -> item.digitToInt() }
            }

        val tilePlus1 = tile.incrementTile()
        val tilePlus2 = tilePlus1.incrementTile()
        val tilePlus3 = tilePlus2.incrementTile()
        val tilePlus4 = tilePlus3.incrementTile()

        val tileRow = tile.joinRight(tilePlus1).joinRight(tilePlus2).joinRight(tilePlus3).joinRight(tilePlus4)

        val tileRowPlus1 = tileRow.incrementTile()
        val tileRowPlus2 = tileRowPlus1.incrementTile()
        val tileRowPlus3 = tileRowPlus2.incrementTile()
        val tileRowPlus4 = tileRowPlus3.incrementTile()

        val allTiles = tileRow.joinBottom(tileRowPlus1).joinBottom(tileRowPlus2).joinBottom(tileRowPlus3).joinBottom(tileRowPlus4)

        return allTiles.map { it.joinToString("") }
    }
}

fun main() {
    val input = readInput("/day15/input.txt")

    val part1 = Chiton(PART_1).findLowestPathRisk(input) // 745
    println("Part 1 result: $part1")

    val timeInMillis = measureTimeMillis {
        val part2 = Chiton(PART_2).findLowestPathRisk(input) // 3002
        println("Part 2 result: $part2")
    }
    println("Part 2 took ${timeInMillis}ms to find")
}