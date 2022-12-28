package advent.of.code.utils

data class Coordinate(val x: Int, val y: Int) {

    fun getNeighbours(minX: Int = 0, minY: Int = 0, maxX: Int = Int.MAX_VALUE, maxY: Int = Int.MAX_VALUE): List<Coordinate> {
        return listOf(
            this.copy(x = x - 1),
            this.copy(x = x + 1),
            this.copy(y = y - 1),
            this.copy(y = y + 1),
        ).filter { it.x >= minX && it.y >= minY && it.x <= maxX && it.y <= maxY }
    }
}
