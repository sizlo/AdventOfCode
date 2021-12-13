package advent.of.code.day11

import advent.of.code.utils.Coordinate
import advent.of.code.utils.readInput

class DumboOctopus {

    class Octopus(var energy: Int, private val coordinate: Coordinate, private val octopusGrid: OctopusGrid) {

        var numFlashes = 0

        fun incrementEnergy() {
            energy++

            if (energy == 10) {
                numFlashes++
                getAllNeighbours().forEach { it.incrementEnergy() }
            }
        }

        fun resetEnergy() {
            if (energy > 9) {
                energy = 0
            }
        }

        private fun getAllNeighbours(): List<Octopus> {
            return coordinate
                .getNeighbourCoordinates(includeDiagonals = true)
                .mapNotNull { octopusGrid.getOctopusAt(it) }
        }
    }

    class OctopusGrid(input: List<String>) {

        private val octopuses = mutableListOf<Octopus>()

        init {
            input.forEachIndexed { y, row ->
                row.toList().map { it.digitToInt() }.forEachIndexed { x, energy ->
                    this.octopuses.add(Octopus(energy, Coordinate(x, y), this))
                }
            }
        }

        private val gridSize = input.size

        fun doStep() {
            octopuses
                .onEach { it.incrementEnergy() }
                .onEach { it.resetEnergy() }
        }

        fun getOctopusAt(coordinate: Coordinate): Octopus? {
            if (coordinate.x < 0 || coordinate.y < 0 || coordinate.x >= gridSize || coordinate.y >= gridSize) {
                return null
            }
            return octopuses[coordinate.y * gridSize + coordinate.x]
        }

        fun getTotalFlashes(): Int = octopuses.sumOf { it.numFlashes }

        fun allHaveFlashedOnThisStep(): Boolean = octopuses.all { it.energy == 0 }
    }

    fun getTotalFlashesAfter100Steps(input: List<String>): Int {
        val octopusGrid = OctopusGrid(input)
        repeat (100) {
            octopusGrid.doStep()
        }
        return octopusGrid.getTotalFlashes()
    }

    fun getFirstStepWhereAllOctopusesFlash(input: List<String>): Int {
        val octopusGrid = OctopusGrid(input)

        var step = 1
        while (true) {
            octopusGrid.doStep()
            if (octopusGrid.allHaveFlashedOnThisStep()) {
                return step
            }
            step++
        }
    }
}

fun main() {
    val input = readInput("/day11/input.txt")
    println(DumboOctopus().getTotalFlashesAfter100Steps(input)) // 1705
    println(DumboOctopus().getFirstStepWhereAllOctopusesFlash(input)) // 265
}