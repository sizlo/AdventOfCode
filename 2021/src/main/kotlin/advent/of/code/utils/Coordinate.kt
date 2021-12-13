package advent.of.code.utils

data class Coordinate(val x: Int, val y: Int) {
    constructor (commaSeparatedXAndY: String): this(
        x = commaSeparatedXAndY.split(",").toIntList()[0],
        y = commaSeparatedXAndY.split(",").toIntList()[1]
    )
}