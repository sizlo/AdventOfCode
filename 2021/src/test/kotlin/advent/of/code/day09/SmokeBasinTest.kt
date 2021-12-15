package advent.of.code.day09

import assertk.assertThat
import assertk.assertions.isEqualTo
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
}