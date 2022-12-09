package advent.of.code.day02

import advent.of.code.utils.Part
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class RockPaperScissorsTest {

    companion object {
        @JvmStatic
        fun getPart1GameTestParameters(): List<Arguments> {
            return listOf(
                Arguments.of("A Y", 8),
                Arguments.of("B X", 1),
                Arguments.of("C Z", 6),
            )
        }

        @JvmStatic
        fun getPart2GameTestParameters(): List<Arguments> {
            return listOf(
                Arguments.of("A Y", 4),
                Arguments.of("B X", 1),
                Arguments.of("C Z", 7),
            )
        }
    }

    private val exampleInput = listOf(
        "A Y",
        "B X",
        "C Z",
    )

    @Test
    fun `test example input for part 1`() {
        val strategyGuide = StrategyGuide.fromStrings(exampleInput, Part.PART_1)
        assertThat(strategyGuide.calculateTotalScore()).isEqualTo(15)
    }

    @Test
    fun `test example input for part 2`() {
        val strategyGuide = StrategyGuide.fromStrings(exampleInput, Part.PART_2)
        assertThat(strategyGuide.calculateTotalScore()).isEqualTo(12)
    }

    @ParameterizedTest(name = "game {0} should have score {1}")
    @MethodSource("getPart1GameTestParameters")
    fun `calculates correct score for game in part 1`(gameString: String, expectedScore: Int) {
        assertThat(Game.fromString(gameString, Part.PART_1).calculateScore()).isEqualTo(expectedScore)
    }

    @ParameterizedTest(name = "game {0} should have score {1}")
    @MethodSource("getPart2GameTestParameters")
    fun `calculates correct score for game in part 2`(gameString: String, expectedScore: Int) {
        assertThat(Game.fromString(gameString, Part.PART_2).calculateScore()).isEqualTo(expectedScore)
    }

}