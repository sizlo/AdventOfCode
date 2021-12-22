package advent.of.code.day22

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEmpty
import org.junit.jupiter.api.Test

internal class TestCubeRange {
    @Test
    fun `test remove completely inside overlap`() {
        val lhs = CubeRange(xRange = 1 .. 10, yRange = 6 .. 15, zRange = 25 .. 35)
        val rhs = CubeRange(xRange = 3 .. 5, yRange = 10 .. 12, zRange = 28 .. 30)

        val result = lhs.removeOverlap(rhs)

        assertThat(result).containsExactlyInAnyOrder(
            CubeRange(xRange = 1 .. 2, yRange = 6 .. 9, zRange = 25 .. 27),
            CubeRange(xRange = 1 .. 2, yRange = 6 .. 9, zRange = 28 .. 30),
            CubeRange(xRange = 1 .. 2, yRange = 6 .. 9, zRange = 31 .. 35),
            CubeRange(xRange = 1 .. 2, yRange = 10 .. 12, zRange = 25 .. 27),
            CubeRange(xRange = 1 .. 2, yRange = 10 .. 12, zRange = 28 .. 30),
            CubeRange(xRange = 1 .. 2, yRange = 10 .. 12, zRange = 31 .. 35),
            CubeRange(xRange = 1 .. 2, yRange = 13 .. 15, zRange = 25 .. 27),
            CubeRange(xRange = 1 .. 2, yRange = 13 .. 15, zRange = 28 .. 30),
            CubeRange(xRange = 1 .. 2, yRange = 13 .. 15, zRange = 31 .. 35),
            CubeRange(xRange = 3 .. 5, yRange = 6 .. 9, zRange = 25 .. 27),
            CubeRange(xRange = 3 .. 5, yRange = 6 .. 9, zRange = 28 .. 30),
            CubeRange(xRange = 3 .. 5, yRange = 6 .. 9, zRange = 31 .. 35),
            CubeRange(xRange = 3 .. 5, yRange = 10 .. 12, zRange = 25 .. 27),
            CubeRange(xRange = 3 .. 5, yRange = 10 .. 12, zRange = 31 .. 35),
            CubeRange(xRange = 3 .. 5, yRange = 13 .. 15, zRange = 25 .. 27),
            CubeRange(xRange = 3 .. 5, yRange = 13 .. 15, zRange = 28 .. 30),
            CubeRange(xRange = 3 .. 5, yRange = 13 .. 15, zRange = 31 .. 35),
            CubeRange(xRange = 6 .. 10, yRange = 6 .. 9, zRange = 25 .. 27),
            CubeRange(xRange = 6 .. 10, yRange = 6 .. 9, zRange = 28 .. 30),
            CubeRange(xRange = 6 .. 10, yRange = 6 .. 9, zRange = 31 .. 35),
            CubeRange(xRange = 6 .. 10, yRange = 10 .. 12, zRange = 25 .. 27),
            CubeRange(xRange = 6 .. 10, yRange = 10 .. 12, zRange = 28 .. 30),
            CubeRange(xRange = 6 .. 10, yRange = 10 .. 12, zRange = 31 .. 35),
            CubeRange(xRange = 6 .. 10, yRange = 13 .. 15, zRange = 25 .. 27),
            CubeRange(xRange = 6 .. 10, yRange = 13 .. 15, zRange = 28 .. 30),
            CubeRange(xRange = 6 .. 10, yRange = 13 .. 15, zRange = 31 .. 35),
        )
    }

    @Test
    fun `test remove complete overlap`() {
        val lhs = CubeRange(xRange = 5 .. 10, yRange = 6 .. 15, zRange = 25 .. 35)
        val rhs = CubeRange(xRange = 3 .. 12, yRange = 4 .. 16, zRange = 25 .. 35)

        val result = lhs.removeOverlap(rhs)

        assertThat(result).isEmpty()
    }

    @Test
    fun `test remove more negative overlap`() {
        val lhs = CubeRange(xRange = 5 .. 10, yRange = 6 .. 15, zRange = 25 .. 35)
        val rhs = CubeRange(xRange = 3 .. 7, yRange = 4 .. 12, zRange = 20 .. 30)

        val result = lhs.removeOverlap(rhs)

        assertThat(result).containsExactlyInAnyOrder(
            CubeRange(xRange = 5 .. 7, yRange = 6 .. 12, zRange = 31 .. 35),
            CubeRange(xRange = 5 .. 7, yRange = 13 .. 15, zRange = 25 .. 30),
            CubeRange(xRange = 5 .. 7, yRange = 13 .. 15, zRange = 31 .. 35),
            CubeRange(xRange = 8 .. 10, yRange = 6 .. 12, zRange = 25 .. 30),
            CubeRange(xRange = 8 .. 10, yRange = 6 .. 12, zRange = 31 .. 35),
            CubeRange(xRange = 8 .. 10, yRange = 13 .. 15, zRange = 25 .. 30),
            CubeRange(xRange = 8 .. 10, yRange = 13 .. 15, zRange = 31 .. 35),
        )
    }

    @Test
    fun `test remove more positive overlap`() {
        val lhs = CubeRange(xRange = 3 .. 7, yRange = 4 .. 12, zRange = 20 .. 30)
        val rhs = CubeRange(xRange = 5 .. 10, yRange = 6 .. 15, zRange = 25 .. 35)

        val result = lhs.removeOverlap(rhs)

        assertThat(result).containsExactlyInAnyOrder(
            CubeRange(xRange = 3 .. 4, yRange = 4 .. 5, zRange = 20 .. 24),
            CubeRange(xRange = 3 .. 4, yRange = 4 .. 5, zRange = 25 .. 30),
            CubeRange(xRange = 3 .. 4, yRange = 6 .. 12, zRange = 20 .. 24),
            CubeRange(xRange = 3 .. 4, yRange = 6 .. 12, zRange = 25 .. 30),
            CubeRange(xRange = 5 .. 7, yRange = 4 .. 5, zRange = 20 .. 24),
            CubeRange(xRange = 5 .. 7, yRange = 4 .. 5, zRange = 25 .. 30),
            CubeRange(xRange = 5 .. 7, yRange = 6 .. 12, zRange = 20 .. 24),
        )
    }
}