package advent.of.code.day11

import advent.of.code.utils.*

class DumboOctopus {

    class Octopus(var energy: Int, coordinate: Coordinate, octopusGrid: OctopusGrid)
        : GridItem<Octopus>(coordinate = coordinate, grid = octopusGrid) {

        var numFlashes = 0

        fun incrementEnergy() {
            energy++

            if (energy == 10) {
                numFlashes++
                getAllNeighbours(includeDiagonals = true).forEach { it.incrementEnergy() }
            }
        }

        fun resetEnergy() {
            if (energy > 9) {
                energy = 0
            }
        }
    }

    class OctopusGrid(input: IntGrid): Grid<Octopus>(input) {

        init {
            populate { coordinate, energyValue -> Octopus(energyValue, coordinate, this) }
        }

        fun doStep() {
            items
                .onEach { it.incrementEnergy() }
                .onEach { it.resetEnergy() }
        }

        fun getTotalFlashes(): Int = items.sumOf { it.numFlashes }

        fun allHaveFlashedOnThisStep(): Boolean = items.all { it.energy == 0 }
    }

    fun getTotalFlashesAfter100Steps(input: IntGrid): Int {
        val octopusGrid = OctopusGrid(input)
        repeat (100) {
            octopusGrid.doStep()
        }
        return octopusGrid.getTotalFlashes()
    }

    fun getFirstStepWhereAllOctopusesFlash(input: IntGrid): Int {
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
    val input = readInputAsIntGrid("/day11/input.txt")
    println(DumboOctopus().getTotalFlashesAfter100Steps(input)) // 1705
    println(DumboOctopus().getFirstStepWhereAllOctopusesFlash(input)) // 265
}