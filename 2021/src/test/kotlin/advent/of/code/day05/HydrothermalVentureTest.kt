package advent.of.code.day05

import advent.of.code.day05.HydrothermalVenture.Line
import advent.of.code.utils.Coordinate
import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class HydrothermalVentureTest {

    private val exampleInput = listOf(
        "0,9 -> 5,9",
        "8,0 -> 0,8",
        "9,4 -> 3,4",
        "2,2 -> 2,1",
        "7,0 -> 7,4",
        "6,4 -> 2,0",
        "0,9 -> 2,9",
        "3,4 -> 1,4",
        "0,0 -> 8,8",
        "5,5 -> 8,2",
    )

    private val testSubject = HydrothermalVenture(exampleInput)

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.countPointsCoveredByNonDiagonalLines(minimumOverlap = 2)

        assertThat(result).isEqualTo(5)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.countPointsCoveredByLines(minimumOverlap = 2)

        assertThat(result).isEqualTo(12)
    }

    @Nested
    inner class LineTest {

        @Test
        fun `gets all covered coordinates for horizontal line`() {
            val line = Line(Pair(Coordinate(1,5), Coordinate(6,5)))

            val result = line.getCoveredCoordinates()

            assertThat(result).containsExactly(
                Coordinate(1, 5),
                Coordinate(2, 5),
                Coordinate(3, 5),
                Coordinate(4, 5),
                Coordinate(5, 5),
                Coordinate(6, 5)
            )
        }

        @Test
        fun `gets all covered coordinates for vertical line`() {
            val line = Line(Pair(Coordinate(1,5), Coordinate(1,10)))

            val result = line.getCoveredCoordinates()

            assertThat(result).containsExactly(
                Coordinate(1, 5),
                Coordinate(1, 6),
                Coordinate(1, 7),
                Coordinate(1, 8),
                Coordinate(1, 9),
                Coordinate(1, 10)
            )
        }

        @Test
        fun `gets all covered coordinates for diagonal line`() {
            val line = Line(Pair(Coordinate(4,5), Coordinate(1,2)))

            val result = line.getCoveredCoordinates()

            assertThat(result).containsExactly(
                Coordinate(4, 5),
                Coordinate(3, 4),
                Coordinate(2, 3),
                Coordinate(1, 2),
            )
        }
    }
}