package advent.of.code.day01

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SonarSweepTest {

    private lateinit var testSubject: SonarSweep

    private val exampleInput = listOf(
            199,
            200,
            208,
            210,
            200,
            207,
            240,
            269,
            260,
            263,
    )

    @BeforeEach
    fun setup() {
        testSubject = SonarSweep()
    }

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.part1(exampleInput)

        assertThat(result).isEqualTo(7)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.part2(exampleInput)

        assertThat(result).isEqualTo(5)
    }
}