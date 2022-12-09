package advent.of.code.utils

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class ReadInputTest {

    @Test
    fun `test read input`() {
        val result = readInput("/test.txt")

        assertThat(result).isEqualTo("line one\nline two\nline three")
    }

    @Test
    fun `test read input lines`() {
        val result = readInputLines("/test.txt")

        assertThat(result).containsExactly("line one", "line two", "line three")
    }

    @Test
    fun `test read input as one list of integers`() {
        val result = readInputAsOneListOfIntegers("/test-ints.txt")

        assertThat(result).containsExactly(1, 2, 3, 2, 1)
    }

    @Test
    fun `test read input as int grid`() {
        val result = readInputAsIntGrid("/test-grid.txt")

        assertThat(result).containsExactly(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
        )
    }
}