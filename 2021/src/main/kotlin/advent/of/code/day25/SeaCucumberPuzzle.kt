package advent.of.code.day25

import advent.of.code.utils.Coordinate
import advent.of.code.utils.readInputLines
import java.lang.RuntimeException

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
            .toMap()
            .filter { it.value != null }
            .toMutableMap()

        private val width = input[0].length
        private val height = input.size

        fun step(): Boolean {
            val movedEast = applyMovements(SeaCucumber.EAST)
            val movedSouth = applyMovements(SeaCucumber.SOUTH)
            return movedEast || movedSouth
        }

        private fun applyMovements(direction: SeaCucumber): Boolean {
            val movements = seaCucumbers
                .filter { it.value == direction }
                .mapValues { it.key.next() }
                .filter { isEmptyAt(it.value) }

            movements.forEach { (oldCoordinate, newCoordinate) ->
                seaCucumbers.remove(oldCoordinate)
                seaCucumbers[newCoordinate] = direction
            }

            return movements.isNotEmpty()
        }

        private fun Coordinate.next(): Coordinate {
            return when (seaCucumbers[this]) {
                SeaCucumber.EAST -> this.copy(x = (this.x + 1) % width)
                SeaCucumber.SOUTH -> this.copy(y = (this.y + 1) % height)
                else -> throw RuntimeException()
            }
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