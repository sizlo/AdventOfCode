package advent.of.code.day09

import advent.of.code.utils.readInput

class SmokeBasin(input: List<String>) {

    class Cell(private val x: Int, private val y: Int, val heightValue: Int, private val heightMap: HeightMap) {
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
            val neighbourCoordinates = listOf(
                Pair(x - 1, y),
                Pair(x + 1, y),
                Pair(x, y - 1),
                Pair(x, y + 1),
            )

            return neighbourCoordinates
                .mapNotNull { (neighbourX, neighbourY) -> heightMap.getCellAt(neighbourX, neighbourY) }
        }
    }

    class HeightMap(input: List<String>) {

        private val cells = mutableListOf<Cell>()
        private val cellsPerRow: Int
        private val numRows: Int

        init {
            input.forEachIndexed { y, row ->
                row.toList().map { it.digitToInt() }.forEachIndexed { x, heightValue ->
                    this.cells.add(Cell(x, y, heightValue, this))
                }
            }
            this.cellsPerRow = input[0].length
            this.numRows = input.size
        }

        fun getLowPoints(): List<Cell> {
            return cells.filter { it.isLowPoint() }
        }

        fun getCellAt(x: Int, y: Int): Cell? {
            if (x < 0 || y < 0 || x >= cellsPerRow || y >= numRows) return null
            return cells[y * cellsPerRow + x]
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