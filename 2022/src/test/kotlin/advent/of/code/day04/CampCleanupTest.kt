package advent.of.code.day04

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class CampCleanupTest {

    private val exampleInput = listOf(
        "2-4,6-8",
        "2-3,4-5",
        "5-7,7-9",
        "2-8,3-7",
        "6-6,4-6",
        "2-6,4-8",
    )

    private val testSubject = CampCleanup()

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.countPairsWhereOneRangeFullyContainsAnother(exampleInput)
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.countPairsWhereRangesOverlap(exampleInput)
        assertThat(result).isEqualTo(4)
    }
}
