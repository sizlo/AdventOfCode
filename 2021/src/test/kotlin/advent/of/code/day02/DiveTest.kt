package advent.of.code.day02

import advent.of.code.utils.Part.PART_1
import advent.of.code.utils.Part.PART_2
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class DiveTest {

    private val exampleInput = listOf(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2",
    )

    @Test
    fun `test example input for part 1`() {
        val testSubject = Dive(PART_1)

        testSubject.applyCommands(exampleInput)

        assertThat(testSubject.position).isEqualTo(Position(depth = 10, horizontal = 15))
        assertThat(testSubject.position.multiply()).isEqualTo(150)
    }

    @Test
    fun `test example input for part 2`() {
        val testSubject = Dive(PART_2)

        testSubject.applyCommands(exampleInput)

        assertThat(testSubject.position).isEqualTo(Position(depth = 60, horizontal = 15))
        assertThat(testSubject.position.multiply()).isEqualTo(900)
    }
}