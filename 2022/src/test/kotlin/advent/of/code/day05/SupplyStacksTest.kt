package advent.of.code.day05

import advent.of.code.utils.Part
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class SupplyStacksTest {

    private val exampleInput = listOf(
        "    [D]",
        "[N] [C]",
        "[Z] [M] [P]",
        " 1   2   3",
        "",
        "move 1 from 2 to 1",
        "move 3 from 1 to 3",
        "move 2 from 2 to 1",
        "move 1 from 1 to 2",
    )

    private val testSubject = SupplyStacks()

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.findTopCratesAfterRearrangementAsAString(exampleInput, Part.PART_1)
        assertThat(result).isEqualTo("CMZ")
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.findTopCratesAfterRearrangementAsAString(exampleInput, Part.PART_2)
        assertThat(result).isEqualTo("MCD")
    }
}