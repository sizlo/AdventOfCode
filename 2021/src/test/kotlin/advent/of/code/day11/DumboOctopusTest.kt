package advent.of.code.day11

import advent.of.code.day11.DumboOctopus.OctopusGrid
import advent.of.code.utils.toIntGrid
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class DumboOctopusTest {

    private val exampleInput = listOf(
        "5483143223",
        "2745854711",
        "5264556173",
        "6141336146",
        "6357385478",
        "4167524645",
        "2176841721",
        "6882881134",
        "4846848554",
        "5283751526",
    ).toIntGrid()

    private val testSubject = DumboOctopus()

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.getTotalFlashesAfter100Steps(exampleInput)

        assertThat(result).isEqualTo(1656)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.getFirstStepWhereAllOctopusesFlash(exampleInput)

        assertThat(result).isEqualTo(195)
    }

    @Nested
    inner class OctopusGridTest {
        @Test
        fun `test smaller example input`() {
            val exampleInput = listOf(
                "11111",
                "19991",
                "19191",
                "19991",
                "11111",
            ).toIntGrid()

            val testSubject = OctopusGrid(exampleInput)
            println(testSubject)

            testSubject.doStep()
            assertThat(testSubject.getTotalFlashes()).isEqualTo(9)

            testSubject.doStep()
            assertThat(testSubject.getTotalFlashes()).isEqualTo(9)
        }
    }
}