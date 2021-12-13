package advent.of.code.day12

import advent.of.code.utils.Part
import advent.of.code.utils.Part.*
import advent.of.code.utils.readInput

typealias Path = MutableList<PassagePathing.Cave>

fun Path.canVisit(cave: PassagePathing.Cave, part: Part): Boolean {
    val alreadyVisitedASmallCaveMultipleTimes = this
        .filter { !it.isBig }
        .groupBy { it.name }
        .map { it.value.size }
        .any { it > 1 }

    return when (part) {
        PART_1 -> cave.isBig || !this.contains(cave)
        PART_2 -> cave.isBig || !this.contains(cave) || !alreadyVisitedASmallCaveMultipleTimes
    }
}

class PassagePathing(private val part: Part) {

    class Cave(val name: String) {
        val isBig = name.uppercase() == name
        val connectedCaves = mutableListOf<Cave>()

        fun addConnectedCave(connectedCave: Cave) {
            if (connectedCave.name != "start") {
                connectedCaves.add(connectedCave)
            }
        }
    }

    fun countAllPossiblePaths(input: List<String>): Int {
        val allCaves = createCavesWithoutAnyConnections(input)
        addConnectionsToCaves(allCaves, input)

        val startCave = allCaves["start"]!!
        val allPaths = findAllPaths(currentPath = mutableListOf(startCave))

        return allPaths.size
    }

    private fun createCavesWithoutAnyConnections(input: List<String>): Map<String, Cave> {
        return input
            .flatMap { it.split("-") }
            .distinct()
            .associateWith { Cave(it) }
    }

    private fun addConnectionsToCaves(allCaves: Map<String, Cave>, input: List<String>) {
        input
            .map { it.split("-") }
            .forEach { (lhsName, rhsName) ->
                val lhsCave = allCaves[lhsName]!!
                val rhsCave = allCaves[rhsName]!!
                lhsCave.addConnectedCave(rhsCave)
                rhsCave.addConnectedCave(lhsCave)
            }
    }

    private fun findAllPaths(
        currentPath: Path,
        allPaths: MutableList<Path> = mutableListOf()
    ): List<Path> {
        val currentCave = currentPath.last()

        if (currentCave.name == "end") {
            allPaths.add(currentPath)
            return allPaths
        }

        currentCave.connectedCaves
            .filter { currentPath.canVisit(it, part) }
            .forEach { connectedCave ->
                val nextPath = currentPath.toMutableList().also{ it.add(connectedCave) }
                findAllPaths(currentPath = nextPath, allPaths = allPaths)
            }

        return allPaths
    }
}

fun main() {
    val input = readInput("/day12/input.txt")
    println(PassagePathing(PART_1).countAllPossiblePaths(input)) // 4885
    println(PassagePathing(PART_2).countAllPossiblePaths(input)) // 117095
}