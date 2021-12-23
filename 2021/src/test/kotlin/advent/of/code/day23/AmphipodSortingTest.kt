package advent.of.code.day23

import advent.of.code.utils.Part
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

internal class AmphipodSortingTest {

    private val exampleInput = listOf(
        "#############",
        "#...........#",
        "###B#C#B#D###",
        "  #A#D#C#A#",
        "  #########",
    )

    @Test
    fun `test example input for part 1`() {
        val result = AmphipodSorting(Part.PART_1).findLeastAmountOfEnergyToSort(exampleInput)

        assertThat(result).isEqualTo(12521)
    }

    @Disabled // This takes 12 minutes
    @Test
    fun `test example input for part 2`() {
        val result = AmphipodSorting(Part.PART_2).findLeastAmountOfEnergyToSort(exampleInput)

        assertThat(result).isEqualTo(44169)
    }
}