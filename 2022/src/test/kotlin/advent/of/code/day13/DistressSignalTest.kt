package advent.of.code.day13

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class DistressSignalTest {

    private val exampleInput = listOf(
        "[1,1,3,1,1]",
        "[1,1,5,1,1]",
        "",
        "[[1],[2,3,4]]",
        "[[1],4]",
        "",
        "[9]",
        "[[8,7,6]]",
        "",
        "[[4,4],4,4]",
        "[[4,4],4,4,4]",
        "",
        "[7,7,7,7]",
        "[7,7,7]",
        "",
        "[]",
        "[3]",
        "",
        "[[[]]]",
        "[[]]",
        "",
        "[1,[2,[3,[4,[5,6,7]]]],8,9]",
        "[1,[2,[3,[4,[5,6,0]]]],8,9]",
    )

    private val testSubject = DistressSignal()

    @Test
    fun `test example input for part 1`() {
        assertThat(testSubject.getSumOfIndexesOfPairsInCorrectOrder(exampleInput)).isEqualTo(13)
    }

    @Test
    fun `test example input for part 2`() {
        assertThat(testSubject.findDecoderKey(exampleInput)).isEqualTo(140)
    }
}