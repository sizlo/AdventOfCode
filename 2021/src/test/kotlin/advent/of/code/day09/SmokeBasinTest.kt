package advent.of.code.day09

import advent.of.code.day09.SmokeBasin.HeightMap
import advent.of.code.utils.Coordinate
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNull
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class SmokeBasinTest {

    private val exampleInput = listOf(
        "2199943210",
        "3987894921",
        "9856789892",
        "8767896789",
        "9899965678",
    )

    private val testSubject = SmokeBasin(exampleInput)

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.findSumOfRiskLevelsOfAllLowPoints()

        assertThat(result).isEqualTo(15)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.multiplySizesOfThreeLargestBasins()

        assertThat(result).isEqualTo(1134)
    }

    @Nested
    inner class HeightMapTest {

        private val testSubject = HeightMap(exampleInput)

        @Test
        fun `test parsing input`() {
            assertThat(testSubject.getCellAt(Coordinate(0, 0))?.heightValue).isEqualTo(2)
            assertThat(testSubject.getCellAt(Coordinate(9, 0))?.heightValue).isEqualTo(0)
            assertThat(testSubject.getCellAt(Coordinate(0, 4))?.heightValue).isEqualTo(9)
            assertThat(testSubject.getCellAt(Coordinate(9, 4))?.heightValue).isEqualTo(8)
            assertThat(testSubject.getCellAt(Coordinate(2, 2))?.heightValue).isEqualTo(5)
            assertThat(testSubject.getCellAt(Coordinate(5, 3))?.heightValue).isEqualTo(9)
        }

        @Test
        fun `should return null for out of bounds coordinates` () {
            assertThat(testSubject.getCellAt(Coordinate(-1, 0))).isNull()
            assertThat(testSubject.getCellAt(Coordinate(10, 0))).isNull()
            assertThat(testSubject.getCellAt(Coordinate(0, -1))).isNull()
            assertThat(testSubject.getCellAt(Coordinate(0, 5))).isNull()
        }
    }
}