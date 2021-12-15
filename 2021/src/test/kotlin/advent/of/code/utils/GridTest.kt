package advent.of.code.utils

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import org.junit.jupiter.api.Test

internal class GridTest {

    private val testSubject = Grid(width = 5, height = 3, items = mutableListOf(
        1, 2, 3, 4, 5,
        6, 7, 8, 9, 10,
        11, 12, 13, 14, 15
    ))

    @Test
    fun `should return null when x is too low`() {
        assertThat(testSubject.getItemAt(Coordinate(-1, 1))).isNull()
    }

    @Test
    fun `should return null when x is too high`() {
        assertThat(testSubject.getItemAt(Coordinate(5, 1))).isNull()
    }

    @Test
    fun `should return null when y is too low`() {
        assertThat(testSubject.getItemAt(Coordinate(2, -1))).isNull()
    }

    @Test
    fun `should return null when y is too high`() {
        assertThat(testSubject.getItemAt(Coordinate(2, 3))).isNull()
    }

    @Test
    fun `should return correct item for coordinates`() {
        assertThat(testSubject.getItemAt(Coordinate(0, 0))).isEqualTo(1)
        assertThat(testSubject.getItemAt(Coordinate(4, 0))).isEqualTo(5)
        assertThat(testSubject.getItemAt(Coordinate(0, 2))).isEqualTo(11)
        assertThat(testSubject.getItemAt(Coordinate(4, 2))).isEqualTo(15)
        assertThat(testSubject.getItemAt(Coordinate(2, 1))).isEqualTo(8)
        assertThat(testSubject.getItemAt(Coordinate(3, 2))).isEqualTo(14)
    }
}