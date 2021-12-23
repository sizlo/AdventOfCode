package advent.of.code.utils

import kotlin.math.absoluteValue

data class Coordinate(val x: Int, val y: Int, val z: Int = 0) {
    constructor (commaSeparated: String): this(
        x = commaSeparated.split(",").toIntList()[0],
        y = commaSeparated.split(",").toIntList()[1],
        z = commaSeparated.split(",").toIntList().getOrElse(2) { 0 }
    )

    fun getNeighbourCoordinates(includeDiagonals: Boolean = false): List<Coordinate> {
        val neighbours = mutableListOf(
            Coordinate(x - 1, y),
            Coordinate(x + 1, y),
            Coordinate(x, y - 1),
            Coordinate(x, y + 1),
        )

        if (includeDiagonals) {
            neighbours.add(Coordinate(x - 1, y - 1))
            neighbours.add(Coordinate(x + 1, y - 1))
            neighbours.add(Coordinate(x - 1, y + 1))
            neighbours.add(Coordinate(x + 1, y + 1))
        }

        return neighbours
    }

    fun below(): Coordinate = this.copy(y = y + 1)
    fun above(): Coordinate = this.copy(y = y - 1)
    fun left(): Coordinate = this.copy(x = x - 1)
    fun right(): Coordinate = this.copy(x = x + 1)

    fun manhattanDistance(other: Coordinate): Int {
        return (this.x - other.x).absoluteValue + (this.y - other.y).absoluteValue + (this.z - other.z).absoluteValue
    }

    operator fun plus(other: Coordinate): Coordinate {
        return Coordinate(
            this.x + other.x,
            this.y + other.y,
            this.z + other.z
        )
    }

    operator fun minus(other: Coordinate): Coordinate {
        return Coordinate(
            this.x - other.x,
            this.y - other.y,
            this.z - other.z
        )
    }
}