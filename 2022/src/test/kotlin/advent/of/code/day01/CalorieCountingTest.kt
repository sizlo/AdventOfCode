package advent.of.code.day01

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class CalorieCountingTest {

    private val exampleInput = listOf(
        listOf(1000, 2000, 3000),
        listOf(4000),
        listOf(5000, 6000),
        listOf(7000, 8000, 9000),
        listOf(10000),
    )

    private val testSubject = CalorieCounting()

    @Test
    fun `test example input for part 1`() {
        assertThat(testSubject.findLargestTotalCaloriesInASingleElfInventory(exampleInput)).isEqualTo(24000)
    }

    @Test
    fun `test example input for part 2`() {
        assertThat(testSubject.findLargestTotalCaloriesInThreeElvesInventories(exampleInput)).isEqualTo(45000)
    }
}