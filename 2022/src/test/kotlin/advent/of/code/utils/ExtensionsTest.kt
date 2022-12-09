package advent.of.code.utils

import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test

internal class ExtensionsTest {

    @Test
    fun `test list to int`() {
        val stringList = listOf("01", "203", "-1")

        val intList = stringList.toIntList()

        assertThat(intList).containsExactly(1, 203, -1)
    }

    @Test
    fun `test string list to int grid`() {
        val stringList = listOf("123", "456", "789")

        val intGrid = stringList.toIntGrid()

        assertThat(intGrid).containsExactly(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
        )
    }

    @Test
    fun `test product of collection`() {
        assertThat(listOf(5L, 8L, 12L).productOf { it } ).isEqualTo(480)
    }

    @Test
    fun `test splitting string list on blank lines`() {
        val stringList = listOf(
            "one",
            "two",
            "",
            "three",
            "",
            "four",
            "five"
        )

        val result = stringList.splitOnBlankLines()

        assertThat(result[0]).containsExactly("one", "two")
        assertThat(result[1]).containsExactly("three")
        assertThat(result[2]).containsExactly("four", "five")
    }
}