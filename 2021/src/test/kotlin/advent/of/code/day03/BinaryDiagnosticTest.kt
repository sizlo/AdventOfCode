package advent.of.code.day03

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class BinaryDiagnosticTest {

    private val exampleInput = listOf(
            "00100",
            "11110",
            "10110",
            "10111",
            "10101",
            "01111",
            "00111",
            "11100",
            "10000",
            "11001",
            "00010",
            "01010",
    )

    private val testSubject = BinaryDiagnostic(exampleInput)

    @Test
    fun `test example input for part 1`() {
        val powerConsumption = testSubject.getPowerConsumption()

        assertThat(powerConsumption).isEqualTo(198)
    }

    @Test
    fun `test example input for part 2`() {
        val powerConsumption = testSubject.getLifeSupportRating()

        assertThat(powerConsumption).isEqualTo(230)
    }
}