package advent.of.code.day25

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class SeaCucumberPuzzleTest {

    private val exampleInput = listOf(
        "v...>>.vv>",
        ".vv>>.vv..",
        ">>.>v>...v",
        ">>v>>.>.v.",
        "v>v.vv.v..",
        ">.>>..v...",
        ".vv..>.>v.",
        "v.v..>>v.v",
        "....v..v.>",
    )

    private val testSubject = SeaCucumberPuzzle()

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.findHowManyStepsItTakesForAllSeaCucumbersToStop(exampleInput)

        assertThat(result).isEqualTo(58)
    }
}