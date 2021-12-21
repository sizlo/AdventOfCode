package advent.of.code.day21

import advent.of.code.day21.DiracDice.*
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class DiracDiceTest {

    private val exampleInput = listOf(
        "Player 1 starting position: 4",
        "Player 2 starting position: 8"
    )

    private val testSubject = DiracDice()

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.multiplyLosingScoreWithNumberOfDiceRolls(exampleInput)

        assertThat(result).isEqualTo(739785)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.findHowManyUniversesOverallWinnerWinsIn(exampleInput)

        assertThat(result).isEqualTo(444356092776315L)
    }

    @Nested
    inner class TestDeterministicDie {

        private val testSubject = DeterministicDie()

        @Test
        fun `test deterministic die produces expected rolls`() {
            repeat(2) {
                (1..100).forEach { expectedRoll ->
                    assertThat(testSubject.roll()).isEqualTo(expectedRoll)
                }
            }
            assertThat(testSubject.totalNumberOfRolls).isEqualTo(200)
        }
    }
}