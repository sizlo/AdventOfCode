package advent.of.code.day09

import advent.of.code.utils.*

class SmokeBasin(input: IntGrid) {

    class Cell(coordinate: Coordinate, private val heightValue: Int, heightMap: HeightMap)
        : GridItem<Cell>(coordinate = coordinate, grid = heightMap) {

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
    }

    class HeightMap(input: IntGrid): Grid<Cell>(input) {

        init {
            populate { coordinate, heightValue -> Cell(coordinate, heightValue, this) }
        }

        fun getLowPoints(): List<Cell> {
            return items.filter { it.isLowPoint() }
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
    val input = readInputAsIntGrid("/day09/input.txt")
    val smokeBasin = SmokeBasin(input)
    println(smokeBasin.findSumOfRiskLevelsOfAllLowPoints()) // 572
    println(smokeBasin.multiplySizesOfThreeLargestBasins()) // 847044
}