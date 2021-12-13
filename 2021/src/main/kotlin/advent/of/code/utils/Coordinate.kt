package advent.of.code.utils

data class Coordinate(val x: Int, val y: Int) {
    constructor (commaSeparatedXAndY: String): this(
        x = commaSeparatedXAndY.split(",").toIntList()[0],
        y = commaSeparatedXAndY.split(",").toIntList()[1]
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
}