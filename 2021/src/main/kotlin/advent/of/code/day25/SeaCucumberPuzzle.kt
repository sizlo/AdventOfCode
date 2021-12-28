package advent.of.code.day25

import advent.of.code.utils.Coordinate
import advent.of.code.utils.readInputLines

class SeaCucumberPuzzle {

    enum class SeaCucumber {
        EAST, SOUTH;

        companion object {
            fun fromChar(char: Char): SeaCucumber? {
                return when (char) {
                    '>' -> EAST
                    'v' -> SOUTH
                    else -> null
                }
            }
        }
    }

    class SeaCucumberHerd(input: List<String>) {

        private val seaCucumbers = input
            .flatMapIndexed { y, row ->
                row.toList().mapIndexed { x, char ->
                    Coordinate(x, y) to SeaCucumber.fromChar(char)
                }
            }
            .filter { it.second != null }
            .associate { it.first to it.second!! }
            .toMutableMap()

        private val width = input[0].length
        private val height = input.size

        fun step(): Boolean {
            val eastMovements = seaCucumbers
                .filter { it.value == SeaCucumber.EAST }
                .mapValues { it.key.rightWithWrap() }
                .filter { isEmptyAt(it.value) }

            eastMovements.forEach { (oldCoordinate, newCoordinate) ->
                seaCucumbers.remove(oldCoordinate)
                seaCucumbers[newCoordinate] = SeaCucumber.EAST
            }

            val southMovements = seaCucumbers
                .filter { it.value == SeaCucumber.SOUTH }
                .mapValues { it.key.bottomWithWrap() }
                .filter { isEmptyAt(it.value) }

            southMovements.forEach { (oldCoordinate, newCoordinate) ->
                seaCucumbers.remove(oldCoordinate)
                seaCucumbers[newCoordinate] = SeaCucumber.SOUTH
            }

            return eastMovements.isNotEmpty() || southMovements.isNotEmpty()
        }

        private fun Coordinate.rightWithWrap(): Coordinate {
            return this.copy(x = (this.x + 1) % width)
        }

        private fun Coordinate.bottomWithWrap(): Coordinate {
            return this.copy(y = (this.y + 1) % height)
        }

        private fun isEmptyAt(coordinate: Coordinate): Boolean {
            return !seaCucumbers.contains(coordinate)
        }
    }

    fun findHowManyStepsItTakesForAllSeaCucumbersToStop(input: List<String>): Int {
        val herd = SeaCucumberHerd(input)

        var step = 0
        do {
            val didMove = herd.step()
            step++
        } while(didMove)

        return step
    }
}

fun main() {
    val input = readInputLines("/day25/input.txt")
    println(SeaCucumberPuzzle().findHowManyStepsItTakesForAllSeaCucumbersToStop(input)) // 456
}