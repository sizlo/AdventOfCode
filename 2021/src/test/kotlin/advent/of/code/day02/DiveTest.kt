package advent.of.code.day02

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DiveTest {

    private lateinit var testSubject: Dive

    private val exampleInput = listOf(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2",
    )

    @BeforeEach
    fun setup() {
        testSubject = Dive()
    }

    @Test
    fun `test example input for part 1`() {
        testSubject.applyCommandsForPart1(exampleInput)

        assertThat(testSubject.position).isEqualTo(Position(depth = 10, horizontal = 15))
        assertThat(testSubject.position.multiply()).isEqualTo(150)
    }

    @Test
    fun `test example input for part 2`() {
        testSubject.applyCommandsForPart2(exampleInput)

        assertThat(testSubject.position).isEqualTo(Position(depth = 60, horizontal = 15))
        assertThat(testSubject.position.multiply()).isEqualTo(900)
    }
}