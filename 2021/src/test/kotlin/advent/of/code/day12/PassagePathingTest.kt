package advent.of.code.day12

import advent.of.code.utils.Part.PART_1
import advent.of.code.utils.Part.PART_2
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class PassagePathingTest {

    private val smallExampleInput = listOf(
        "start-A",
        "start-b",
        "A-c",
        "A-b",
        "b-d",
        "A-end",
        "b-end",
    )

    private val mediumExampleInput = listOf(
        "dc-end",
        "HN-start",
        "start-kj",
        "dc-start",
        "dc-HN",
        "LN-dc",
        "HN-end",
        "kj-sa",
        "kj-HN",
        "kj-dc",
    )

    private val largeExampleInput = listOf(
        "fs-end",
        "he-DX",
        "fs-he",
        "start-DX",
        "pj-DX",
        "end-zg",
        "zg-sl",
        "zg-pj",
        "pj-he",
        "RW-he",
        "fs-DX",
        "pj-RW",
        "zg-RW",
        "start-pj",
        "he-WI",
        "zg-he",
        "pj-fs",
        "start-RW",
    )

    @Test
    fun `test small example input for part 1`() {
        val result = PassagePathing(PART_1).countAllPossiblePaths(smallExampleInput)

        assertThat(result).isEqualTo(10)
    }

    @Test
    fun `test medium example input for part 1`() {
        val result = PassagePathing(PART_1).countAllPossiblePaths(mediumExampleInput)

        assertThat(result).isEqualTo(19)
    }

    @Test
    fun `test large example input for part 1`() {
        val result = PassagePathing(PART_1).countAllPossiblePaths(largeExampleInput)

        assertThat(result).isEqualTo(226)
    }

    @Test
    fun `test small example input for part 2`() {
        val result = PassagePathing(PART_2).countAllPossiblePaths(smallExampleInput)

        assertThat(result).isEqualTo(36)
    }

    @Test
    fun `test medium example input for part 2`() {
        val result = PassagePathing(PART_2).countAllPossiblePaths(mediumExampleInput)

        assertThat(result).isEqualTo(103)
    }

    @Test
    fun `test large example input for part 2`() {
        val result = PassagePathing(PART_2).countAllPossiblePaths(largeExampleInput)

        assertThat(result).isEqualTo(3509)
    }
}