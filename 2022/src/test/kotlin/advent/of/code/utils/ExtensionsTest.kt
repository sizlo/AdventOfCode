package advent.of.code.utils

import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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

    @Test
    fun `test inverting a map`() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 3)

        val result = map.invert()

        assertThat(result[1]).isEqualTo("a")
        assertThat(result[2]).isEqualTo("b")
        assertThat(result[3]).isEqualTo("c")
    }

    @Test
    fun `test splitting a list in half`() {
        val list = listOf(1, 2, 3, 4)

        val result = list.splitInHalf()

        assertThat(result.first).containsExactlyInAnyOrder(1, 2)
        assertThat(result.second).containsExactlyInAnyOrder(3, 4)
    }

    @Test
    fun `throws error when attempting to split a list with an odd number of items  in half`() {
        val list = listOf(1, 2, 3, 4, 5)

        assertThrows<RuntimeException> { list.splitInHalf() }
    }
}