package advent.of.code.utils

data class Graph<T> (
    val vertices: List<T>,
    val getEdges: (T) -> List<T>,
    val getWeight: (Pair<T, T>) -> Int = { _ -> 1 }
)

data class DjikstraResult<T>(val distance: Int, val path: List<T>)

class Djikstras<T>(private val graph: Graph<T>) {

    private data class Node<T>(val key: T) {
        var visited = false
        var distance = Int.MAX_VALUE
        var path = emptyList<T>()
    }

    fun findShortestPaths(start: T, end: T? = null): Map<T, DjikstraResult<T>> {
        val nodes = graph.vertices.map { Node(it) }.associateBy { it.key }
        nodes.getValue(start).distance = 0

        while (nodes.stillNeedsToBeVisited(end) && nodes.hasUnvisitedReachableNodes()) {
            val current = nodes.closestUnvisited()

            graph.getEdges(current.key)
                .filter { nodes.unvisited().contains(it) }
                .map { nodes.getValue(it) }
                .forEach { neighbour ->
                    val distanceThroughCurrent = current.distance + graph.getWeight(current.key to neighbour.key)
                    if (distanceThroughCurrent < neighbour.distance) {
                        neighbour.distance = distanceThroughCurrent
                        neighbour.path = current.path + current.key
                    }
                }

            current.visited = true
        }

        return nodes.mapValues { DjikstraResult(it.value.distance, it.value.path + it.key) }
    }

    fun findShortestPath(start: T, end: T): DjikstraResult<T> {
        return findShortestPaths(start, end).getValue(end)
    }

    private fun Map<T, Node<T>>.unvisited() = filterNot { it.value.visited }
    private fun Map<T, Node<T>>.closestUnvisited() = unvisited().minBy { it.value.distance }.value
    private fun Map<T, Node<T>>.stillNeedsToBeVisited(node: T?) = node?.let { unvisited().contains(node) } ?: true
    private fun Map<T, Node<T>>.hasUnvisitedReachableNodes() = unvisited().any { it.value.distance < Int.MAX_VALUE }
}
