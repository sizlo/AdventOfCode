package advent.of.code.day12

import advent.of.code.utils.Coordinate
import advent.of.code.utils.Djikstras
import advent.of.code.utils.Graph
import advent.of.code.utils.Part
import advent.of.code.utils.readInput

private typealias HeightMap = Map<Coordinate, Int>

class HillClimbingAlgorithm(input: String) {

    private val maxX = input.lines().first().length - 1
    private val maxY = input.lines().size - 1

    private lateinit var start: Coordinate
    private lateinit var end: Coordinate

    private val heightMap = input.lines().flatMapIndexed { y, row ->
        row.toList().mapIndexed { x, char ->
            val coordinate = Coordinate(x, y)

            if (char == 'S') start = coordinate
            if (char == 'E') end = coordinate

            val heightValue = when(char) {
                'S' -> 0
                'E' -> 25
                else -> char - 'a'
            }

            coordinate to heightValue
        }
    }.toMap()

    fun findFewestStepsRequiredToGetToBestSignalFromGivenStartingPoint(): Int {
        val graph = buildGraph(heightMap, Part.PART_1)
        return Djikstras(graph).findShortestPath(start, end).distance
    }

    fun findFewestStepsRequiredToGetToBestSignalFromAnySpaceWithSameHeightAsStartingPoint(): Int {
        val graph = buildGraph(heightMap, Part.PART_2)
        val results = Djikstras(graph).findShortestPaths(end)
        return heightMap
            .filter { it.value == heightMap.getValue(start) }
            .minOf { results.getValue(it.key).distance }
    }

    private fun buildGraph(heightMap: HeightMap, part: Part): Graph<Coordinate> {
        val neighbourFilter = when (part) {
            Part.PART_1 -> { current: Coordinate, neighbour: Coordinate ->
                heightMap.getValue(neighbour) - heightMap.getValue(current) <= 1
            }
            Part.PART_2 -> { current: Coordinate, neighbour: Coordinate ->
                heightMap.getValue(current) - heightMap.getValue(neighbour) <= 1
            }
        }

        return Graph(
            vertices = heightMap.keys.toList(),
            getEdges = { current ->
                current
                    .getNeighbours(maxX = maxX, maxY = maxY)
                    .filter { neighbour -> neighbourFilter(current, neighbour) }
            },
        )
    }


}

fun main() {
    val input = readInput("/day12/input.txt")
    println(HillClimbingAlgorithm(input).findFewestStepsRequiredToGetToBestSignalFromGivenStartingPoint()) // 456
    println(HillClimbingAlgorithm(input).findFewestStepsRequiredToGetToBestSignalFromAnySpaceWithSameHeightAsStartingPoint()) // 454
}
