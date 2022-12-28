package advent.of.code.day12

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class HillClimbingAlgorithmTest {

    private val exampleInput = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
     """.trimIndent().removeSurrounding("\n")

    @Test
    fun `test example input for part 1`() {
        val result = HillClimbingAlgorithm(exampleInput).findFewestStepsRequiredToGetToBestSignalFromGivenStartingPoint()
        assertThat(result).isEqualTo(31)
    }

    @Test
    fun `test example input for part 2`() {
        val result = HillClimbingAlgorithm(exampleInput).findFewestStepsRequiredToGetToBestSignalFromAnySpaceWithSameHeightAsStartingPoint()
        assertThat(result).isEqualTo(29)
    }
}