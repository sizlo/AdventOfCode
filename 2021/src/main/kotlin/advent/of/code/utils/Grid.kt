package advent.of.code.utils

open class GridItem<T>(val coordinate: Coordinate, private val grid: Grid<T>) {
    fun getAllNeighbours(includeDiagonals: Boolean = false): List<T> {
        return coordinate
            .getNeighbourCoordinates(includeDiagonals)
            .mapNotNull {  grid.getItemAt(it) }
    }
}

open class Grid<T>(
    protected val width: Int,
    protected val height: Int,
    protected val items: MutableList<T> = mutableListOf()
) {

    fun getItemAt(coordinate: Coordinate): T? {
        if (coordinate.x < 0 || coordinate.y < 0 || coordinate.x >= width || coordinate.y >= height) {
            return null
        }
        return items[coordinate.y * width + coordinate.x]
    }

}