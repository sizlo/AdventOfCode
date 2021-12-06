package advent.of.code.day06

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class LanternfishTest {

    private val exampleInput = listOf(3, 4, 3, 1, 2)

    private lateinit var testSubject: Lanternfish

    @BeforeEach
    fun setup() {
        testSubject = Lanternfish(exampleInput)
    }

    @Test
    fun `test example input for part 1`() {
        testSubject.simulate(days = 80)

        val result = testSubject.countFish()

        assertThat(result).isEqualTo(5934)
    }

    @Test
    fun `test example input for part 2`() {
        testSubject.simulate(days = 256)

        val result = testSubject.countFish()

        assertThat(result).isEqualTo(26984457539)
    }
}