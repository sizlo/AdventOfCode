package advent.of.code.utils

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class DjikstrasTest {

    private val graph = Graph(
        vertices = listOf('A', 'B', 'C', 'D', 'E'),
        getEdges = { node ->
            when(node) {
                'A' -> listOf('B', 'C', 'E')
                'B' -> listOf('C')
                'C' -> listOf('B', 'D')
                'D' -> listOf('C', 'E')
                'E' -> listOf('A')
                else -> listOf()
            }
        },
        getWeight = { edge ->
            when(edge) {
                'A' to 'B' -> 1
                'A' to 'C' -> 1
                'A' to 'E' -> 7
                'B' to 'C' -> 1
                'C' to 'B' -> 1
                'C' to 'D' -> 2
                'D' to 'C' -> 1
                'D' to 'E' -> 3
                'E' to 'A' -> 1
                else -> 1
            }
        }
    )

    @Test
    fun `returns correct path for A - E`() {
        val result = Djikstras(graph).findShortestPath('A', 'E')
        assertThat(result.distance).isEqualTo(6)
        assertThat(result.path).containsExactly('A', 'C', 'D', 'E')
    }

    @Test
    fun `returns correct path for all nodes from A`() {
        val result = Djikstras(graph).findShortestPaths('A')

        assertThat(result['A']).isEqualTo(DjikstraResult(0, listOf('A')))
        assertThat(result['B']).isEqualTo(DjikstraResult(1, listOf('A', 'B')))
        assertThat(result['C']).isEqualTo(DjikstraResult(1, listOf('A', 'C')))
        assertThat(result['D']).isEqualTo(DjikstraResult(3, listOf('A', 'C', 'D')))
        assertThat(result['E']).isEqualTo(DjikstraResult(6, listOf('A', 'C', 'D', 'E')))
    }
}