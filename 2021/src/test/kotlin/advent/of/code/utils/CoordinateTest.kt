package advent.of.code.utils

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import org.junit.jupiter.api.Test

internal class CoordinateTest {

    @Test
    fun `should return correct neighbour coordinates`() {
        val result = Coordinate(5, 7).getNeighbourCoordinates()

        assertThat(result).containsExactlyInAnyOrder(
            Coordinate(4, 7),
            Coordinate(6, 7),
            Coordinate(5, 6),
            Coordinate(5, 8),
        )
    }

    @Test
    fun `should return correct neighbour coordinates with diagonals`() {
        val result = Coordinate(5, 7).getNeighbourCoordinates(includeDiagonals = true)

        assertThat(result).containsExactlyInAnyOrder(
            Coordinate(4, 7),
            Coordinate(6, 7),
            Coordinate(5, 6),
            Coordinate(5, 8),
            Coordinate(4, 6),
            Coordinate(4, 8),
            Coordinate(6, 6),
            Coordinate(6, 8),
        )
    }
}