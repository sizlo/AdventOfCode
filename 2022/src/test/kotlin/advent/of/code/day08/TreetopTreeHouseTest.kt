package advent.of.code.day08

import advent.of.code.utils.toIntGrid
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class TreetopTreeHouseTest {

    private val exampleInput = listOf(
        "30373",
        "25512",
        "65332",
        "33549",
        "35390",
    ).toIntGrid()

    private val testSubject = TreetopTreeHouse()

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.countTreesVisibleFromOutside(exampleInput)
        assertThat(result).isEqualTo(21)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.findHighestPossibleScenicScore(exampleInput)
        assertThat(result).isEqualTo(8)
    }
}