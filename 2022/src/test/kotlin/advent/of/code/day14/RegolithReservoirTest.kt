package advent.of.code.day14

import advent.of.code.utils.Part
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class RegolithReservoirTest {

    private val exampleInput = listOf(
        "498,4 -> 498,6 -> 496,6",
        "503,4 -> 502,4 -> 502,9 -> 494,9"
    )


    @Test
    fun `test example input for part 1`() {
        val result = RegolithReservoir(exampleInput, Part.PART_1).countUnitsOfSandWhichComeToRest()
        assertThat(result).isEqualTo(24)
    }

    @Test
    fun `test example input for part 2`() {
        val result = RegolithReservoir(exampleInput, Part.PART_2).countUnitsOfSandWhichComeToRest()
        assertThat(result).isEqualTo(93)
    }
}