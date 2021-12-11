package advent.of.code.day11

import advent.of.code.utils.readInput

class DumboOctopus {

    class Octopus(var energy: Int, private val x: Int, private val y: Int, private val octopusGrid: OctopusGrid) {

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
            val neighbourCoords = listOf(
                Pair(x - 1, y - 1),
                Pair(x, y - 1),
                Pair(x + 1, y - 1),
                Pair(x - 1, y),
                Pair(x + 1, y),
                Pair(x - 1, y + 1),
                Pair(x, y + 1),
                Pair(x + 1, y + 1),
            )
            return neighbourCoords.mapNotNull { (neighbourX, neighbourY) ->
                octopusGrid.getOctopusAt(neighbourX, neighbourY)
            }
        }
    }

    class OctopusGrid(input: List<String>) {

        private val octopuses = mutableListOf<Octopus>()

        init {
            input.forEachIndexed { y, row ->
                row.toList().map { it.digitToInt() }.forEachIndexed { x, energy ->
                    this.octopuses.add(Octopus(energy, x, y, this))
                }
            }
        }

        private val gridSize = input.size

        fun doStep() {
            octopuses
                .onEach { it.incrementEnergy() }
                .onEach { it.resetEnergy() }
        }

        fun getOctopusAt(x: Int, y: Int): Octopus? {
            if (x < 0 || y < 0 || x >= gridSize || y >= gridSize) return null
            return octopuses[y * gridSize + x]
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