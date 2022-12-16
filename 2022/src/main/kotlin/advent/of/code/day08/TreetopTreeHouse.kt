package advent.of.code.day08

import advent.of.code.utils.Coordinate
import advent.of.code.utils.IntGrid
import advent.of.code.utils.getColumns
import advent.of.code.utils.getRows
import advent.of.code.utils.productOf
import advent.of.code.utils.readInputAsIntGrid

typealias CoordinateGenerator = (Int) -> Coordinate

class TreetopTreeHouse {
    fun countTreesVisibleFromOutside(trees: IntGrid): Int {
        val visibleFromRows = trees.getRows().flatMapIndexed { y, row ->
            getCoordinatesOfTreesVisibleFromTheStartOfThisLine(row) { x: Int -> Coordinate(x, y) }
        }.toSet()

        val visibleFromReversedRows = trees.getRows().map { it.reversed() }.flatMapIndexed { y, reversedRow ->
            getCoordinatesOfTreesVisibleFromTheStartOfThisLine(reversedRow) { reversedX: Int ->
                Coordinate(reversedRow.size - (reversedX + 1), y)
            }
        }.toSet()

        val visibleFromColumns = trees.getColumns().flatMapIndexed { x, column ->
            getCoordinatesOfTreesVisibleFromTheStartOfThisLine(column) { y: Int -> Coordinate(x, y) }
        }.toSet()

        val visibleFromReversedColumns = trees.getColumns().map { it.reversed() }.flatMapIndexed { x, reversedColumn ->
            getCoordinatesOfTreesVisibleFromTheStartOfThisLine(reversedColumn) { reversedY: Int ->
                Coordinate(x, reversedColumn.size - (reversedY + 1))
            }
        }.toSet()

        return (visibleFromRows + visibleFromReversedRows + visibleFromColumns + visibleFromReversedColumns).size
    }

    fun findHighestPossibleScenicScore(trees: IntGrid): Int {
        val coordinateToViewingDistances = (0 until trees.getRows().size).flatMap { y ->
            (0 until trees.getColumns().size) .map { x->
                Coordinate(x, y)
            }
        }.associateWith { mutableListOf<Int>() }

        trees.getRows().forEachIndexed { y, row ->
            addViewingDistancesForThisLine(row, coordinateToViewingDistances) { x: Int -> Coordinate(x, y) }
        }

        trees.getRows().map { it.reversed() }.forEachIndexed { y, reversedRow ->
            addViewingDistancesForThisLine(reversedRow, coordinateToViewingDistances) { reversedX: Int ->
                Coordinate(reversedRow.size - (reversedX + 1), y)
            }
        }

        trees.getColumns().forEachIndexed { x, column ->
            addViewingDistancesForThisLine(column, coordinateToViewingDistances) { y: Int -> Coordinate(x, y) }
        }

        trees.getColumns().map { it.reversed() }.forEachIndexed { x, reversedColumn ->
            addViewingDistancesForThisLine(reversedColumn, coordinateToViewingDistances) { reversedY: Int ->
                Coordinate(x, reversedColumn.size - (reversedY + 1))
            }
        }

        return coordinateToViewingDistances
            .map { it.value }
            .maxOf { it.productOf { viewingDistance -> viewingDistance.toLong() }.toInt() }
    }

    private fun getCoordinatesOfTreesVisibleFromTheStartOfThisLine(
        line: List<Int>,
        coordinateGenerator: CoordinateGenerator
    ): Set<Coordinate> {
        var max = -1
        val result = mutableSetOf<Coordinate>()
        line.forEachIndexed { index, treeHeight ->
            if (treeHeight > max) {
                max = treeHeight
                result.add(coordinateGenerator(index))
            }
        }
        return result
    }

    private fun addViewingDistancesForThisLine(
        line: List<Int>,
        coordinateToViewingDistances: Map<Coordinate, MutableList<Int>>,
        coordinateGenerator: CoordinateGenerator
    ) {
        val lastIndexOfHeightAtLeast = (0..9).associateWith { 0 }.toMutableMap()

        line.forEachIndexed { index, treeHeight ->
            val currentViewingDistance = index - lastIndexOfHeightAtLeast.getValue(treeHeight)
            coordinateToViewingDistances[coordinateGenerator(index)]?.add(currentViewingDistance)
            (0..treeHeight).forEach { lastIndexOfHeightAtLeast[it] = index }
        }
    }
}

fun main() {
    val input = readInputAsIntGrid("/day08/input.txt")
    println(TreetopTreeHouse().countTreesVisibleFromOutside(input)) // 1681
    println(TreetopTreeHouse().findHighestPossibleScenicScore(input)) // 201684
}
