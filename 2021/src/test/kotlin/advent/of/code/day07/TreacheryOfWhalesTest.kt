package advent.of.code.day07

import advent.of.code.day07.TreacheryOfWhales.*
import advent.of.code.utils.Part.PART_1
import advent.of.code.utils.Part.PART_2
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class TreacheryOfWhalesTest {

    private val exampleInput = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)

    @Test
    fun `test example input for part 1`() {
        val testSubject = TreacheryOfWhales(PART_1)

        val result = testSubject.findMinimumFuelNeededToAlign(exampleInput)

        assertThat(result).isEqualTo(37)
    }

    @Test
    fun `test example input for part 2`() {
        val testSubject = TreacheryOfWhales(PART_2)

        val result = testSubject.findMinimumFuelNeededToAlign(exampleInput)

        assertThat(result).isEqualTo(168)
    }

    @Nested
    inner class FuelUsageCalculatorTest {

        @Nested
        inner class LinearFuelUsageCalculatorTest {

            private val testSubject = LinearFuelUsageCalculator()

            @ParameterizedTest(name = "should use {1} fuel aligning example input to position {0}")
            @CsvSource("2,37", "1,41", "3,39", "10,71")
            fun `test linear fuel usage examples`(positionString: String, expectedResultString: String) {
                assertThat(
                    testSubject.calculateFuelNeededToAlignToPosition(exampleInput, positionString.toInt())
                ).isEqualTo(
                    expectedResultString.toInt()
                )
            }
        }

        @Nested
        inner class ExponentialFuelUsageCalculatorTest {

            private val testSubject = ExponentialFuelUsageCalculator()

            @ParameterizedTest(name = "should use {1} fuel aligning example input to position {0}")
            @CsvSource("5,168", "2,206",)
            fun `test exponential fuel usage examples`(positionString: String, expectedResultString: String) {
                assertThat(
                    testSubject.calculateFuelNeededToAlignToPosition(exampleInput, positionString.toInt())
                ).isEqualTo(
                    expectedResultString.toInt()
                )
            }
        }
    }
}