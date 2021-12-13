package advent.of.code.day13

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class TransparentOrigamiTest {

    private val exampleInput = listOf(
        "6,10",
        "0,14",
        "9,10",
        "0,3",
        "10,4",
        "4,11",
        "6,0",
        "6,12",
        "4,1",
        "0,13",
        "10,12",
        "3,4",
        "3,0",
        "8,4",
        "1,10",
        "2,14",
        "8,10",
        "9,0",
        "",
        "fold along y=7",
        "fold along x=5",
    )

    private val testSubject = TransparentOrigami(exampleInput)

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.countDotsAfter1Fold()

        assertThat(result).isEqualTo(17)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.getImageProducedAfterApplyingAllFolds()

        val expectedImage = """#####
                               #...#
                               #...#
                               #...#
                               #####
        """.replace(" ", "")

        assertThat(result).isEqualTo(expectedImage)
    }
}