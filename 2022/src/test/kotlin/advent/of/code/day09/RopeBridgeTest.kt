package advent.of.code.day09

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class RopeBridgeTest {

    private val exampleInput = listOf(
        "R 4",
        "U 4",
        "L 3",
        "D 1",
        "R 4",
        "D 1",
        "L 5",
        "R 2",
    )

    private val largerExampleInput = listOf(
        "R 5",
        "U 8",
        "L 8",
        "D 3",
        "R 17",
        "D 10",
        "L 25",
        "U 20",
    )

    @Test
    fun `test example input for part 1`() {
        val result = RopeBridge(2).countAllPositionsTailVisits(exampleInput)
        assertThat(result).isEqualTo(13)
    }

    @Test
    fun `test example input for part 2`() {
        val result = RopeBridge(10).countAllPositionsTailVisits(exampleInput)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `test larger example input for part 2`() {
        val result = RopeBridge(10).countAllPositionsTailVisits(largerExampleInput)
        assertThat(result).isEqualTo(36)
    }
}