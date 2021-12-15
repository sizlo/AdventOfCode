package advent.of.code.day15

import advent.of.code.utils.*
import advent.of.code.utils.Part.PART_1
import advent.of.code.utils.Part.PART_2
import java.time.LocalTime
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

    class RiskLevel(coordinate: Coordinate, riskLevelMap: RiskLevelMap, val riskLevel: Int)
        : GridItem<RiskLevel>(coordinate = coordinate, grid = riskLevelMap)

    class RiskLevelMap(input: List<String>): Grid<RiskLevel>(width = input.first().length, height = input.size) {
        init {
            input.forEachIndexed { y, row ->
                row.toList().map { it.digitToInt() }.forEachIndexed { x, riskLevel ->
                    items.add(RiskLevel(Coordinate(x, y), this, riskLevel))
                }
            }
        }

        fun findLowestPathRisk(): Int {
            val unvisited = items.toMutableSet()
            val distances = items.associateWith { Int.MAX_VALUE }.toMutableMap()

            val start = getItemAt(Coordinate(0, 0))!!
            val end = getItemAt(Coordinate(width - 1, height - 1))!!
            distances[start] = 0

            var lastPrintedProgressPercent = Int.MIN_VALUE

            while (unvisited.contains(end)) {
                val current = distances
                    .filter { unvisited.contains((it.key)) }
                    .minByOrNull { it.value }!!
                    .key

                current
                    .getAllNeighbours()
                    .filter { unvisited.contains(it) }
                    .forEach { neighbour ->
                        val distanceToCurrent = neighbour.riskLevel + distances.of(current)
                        distances[neighbour] = minOf(distanceToCurrent, distances.of(neighbour))
                    }
                unvisited.remove(current)

                val visited = items.size - unvisited.size
                val progressPercent = ((visited.toDouble() / items.size.toDouble()) * 100).toInt()
                if (progressPercent > lastPrintedProgressPercent) {
                    println("${LocalTime.now()} Progress: $visited / ${items.size}     ${progressPercent}%")
                    lastPrintedProgressPercent = progressPercent
                }
            }

            return distances.of(end)
        }

        private fun MutableMap<RiskLevel, Int>.of(key: RiskLevel): Int = this[key]!!
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

    val timeinMillis = measureTimeMillis {
        val part2 = Chiton(PART_2).findLowestPathRisk(input) // 3002
        println("Part 2 result: $part2")
    }
    println("Part 2 took ${timeinMillis}ms to find")
}