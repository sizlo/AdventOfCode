package advent.of.code.day09

import advent.of.code.utils.Coordinate
import advent.of.code.utils.readInput

class SmokeBasin(input: List<String>) {

    class Cell(private val coordinate: Coordinate, val heightValue: Int, private val heightMap: HeightMap) {
        fun isLowPoint(): Boolean {
            return getAllNeighbours().all { neighbour -> this.heightValue < neighbour.heightValue }
        }

        fun getRiskValue(): Int = heightValue + 1

        fun getCellsInSameBasin(basin: MutableSet<Cell> = mutableSetOf()): Set<Cell> {
            basin.add(this)

            getAllNeighbours()
                .filter { it.heightValue != 9 }
                .filter { !basin.contains(it) }
                .forEach { it.getCellsInSameBasin(basin) }

            return basin
        }

        private fun getAllNeighbours(): List<Cell> {
            return coordinate
                .getNeighbourCoordinates()
                .mapNotNull {  heightMap.getCellAt(it) }
        }
    }

    class HeightMap(input: List<String>) {

        private val cells = mutableListOf<Cell>()
        private val cellsPerRow: Int
        private val numRows: Int

        init {
            input.forEachIndexed { y, row ->
                row.toList().map { it.digitToInt() }.forEachIndexed { x, heightValue ->
                    this.cells.add(Cell(Coordinate(x, y), heightValue, this))
                }
            }
            this.cellsPerRow = input[0].length
            this.numRows = input.size
        }

        fun getLowPoints(): List<Cell> {
            return cells.filter { it.isLowPoint() }
        }

        fun getCellAt(coordinate: Coordinate): Cell? {
            if (coordinate.x < 0 || coordinate.y < 0 || coordinate.x >= cellsPerRow || coordinate.y >= numRows) {
                return null
            }
            return cells[coordinate.y * cellsPerRow + coordinate.x]
        }
    }

    private val heightMap = HeightMap(input)

    fun findSumOfRiskLevelsOfAllLowPoints(): Int {
        return heightMap
            .getLowPoints()
            .sumOf { it.getRiskValue() }
    }

    fun multiplySizesOfThreeLargestBasins(): Int {
        val basinSizesDescending = heightMap
            .getLowPoints()
            .map { it.getCellsInSameBasin() }
            .map { it.size }
            .sortedDescending()

        return basinSizesDescending[0] * basinSizesDescending[1] * basinSizesDescending[2]
    }
}

fun main() {
    val input = readInput("/day09/input.txt")
    val smokeBasin = SmokeBasin(input)
    println(smokeBasin.findSumOfRiskLevelsOfAllLowPoints()) // 572
    println(smokeBasin.multiplySizesOfThreeLargestBasins()) // 847044
}