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
        val stringList = listOf("123", "456", "789", "012")

        val intGrid = stringList.toIntGrid()

        assertThat(intGrid).containsExactly(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
            listOf(0, 1, 2),
        )
    }

    @Test
    fun `test get int grid rows`() {
        val stringList = listOf("123", "456", "789", "012")

        val intGrid = stringList.toIntGrid()

        assertThat(intGrid.getRows()).containsExactly(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
            listOf(0, 1, 2),
        )
    }

    @Test
    fun `test get int grid columns`() {
        val stringList = listOf("123", "456", "789", "012")

        val intGrid = stringList.toIntGrid()

        assertThat(intGrid.getColumns()).containsExactly(
            listOf(1, 4, 7, 0),
            listOf(2, 5, 8, 1),
            listOf(3, 6, 9, 2),
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

    @Test
    fun `test String to IntRange`() {
        assertThat("2-345".toIntRange()).isEqualTo(2..345)
        assertThrows<RuntimeException> { "-1-45".toIntRange() }
    }

    @Test
    fun `test IntRange fully contains another`() {
        // Fully contained
        assertThat((1..5).fullyContains(2..4)).isTrue()
        assertThat((1..5).fullyContains(1..5)).isTrue()

        // Some before
        assertThat((5..10).fullyContains(1..4)).isFalse()
        assertThat((5..10).fullyContains(1..5)).isFalse()
        assertThat((5..10).fullyContains(1..7)).isFalse()

        // Some after
        assertThat((5..10).fullyContains(11..15)).isFalse()
        assertThat((5..10).fullyContains(10..15)).isFalse()
        assertThat((5..10).fullyContains(7..15)).isFalse()

        // Some before and after
        assertThat((5..10).fullyContains(1..15)).isFalse()
    }

    @Test
    fun `test IntRange overlaps another`() {
        // Same range
        assertThat((1..5).overlaps(1..5)).isTrue()

        // lhs fully contains rhs
        assertThat((1..5).overlaps(2..4)).isTrue()
        assertThat((1..5).overlaps(1..4)).isTrue()
        assertThat((1..5).overlaps(2..5)).isTrue()

        // rhs fully contains lhs
        assertThat((2..4).overlaps(1..5)).isTrue()
        assertThat((1..4).overlaps(1..5)).isTrue()
        assertThat((2..5).overlaps(1..5)).isTrue()

        // lhs starts before rhs
        assertThat((1..7).overlaps(5..10)).isTrue()
        assertThat((1..5).overlaps(5..10)).isTrue()

        // lhs ends after rhs
        assertThat((7..15).overlaps(5..10)).isTrue()
        assertThat((10..15).overlaps(5..10)).isTrue()

        // rhs starts before lhs
        assertThat((5..10).overlaps(1..7)).isTrue()
        assertThat((5..10).overlaps(1..5)).isTrue()

        // rhs ends after lhs
        assertThat((5..10).overlaps(7..15)).isTrue()
        assertThat((5..10).overlaps(10..15)).isTrue()

        // lhs fully before rhs
        assertThat((1..4).overlaps(5..10)).isFalse()

        // lhs fully after rhs
        assertThat((11..15).overlaps(5..10)).isFalse()

        // rhs fully before lhs
        assertThat((5..10).overlaps(1..4)).isFalse()

        // rhs fully after lhs
        assertThat((5..10).overlaps(11..15)).isFalse()
    }

    @Test
    fun `test collection has only unique contents`() {
        assertThat(listOf(1, 2, 3, 4).hasOnlyUniqueContents()).isTrue()
        assertThat(listOf('a', 'b', 'c', 'd').hasOnlyUniqueContents()).isTrue()
        assertThat(listOf("aa", "bb", "cc", "dd").hasOnlyUniqueContents()).isTrue()

        assertThat(listOf(1, 2, 3, 1).hasOnlyUniqueContents()).isFalse()
        assertThat(listOf('a', 'b', 'c', 'a').hasOnlyUniqueContents()).isFalse()
        assertThat(listOf("aa", "bb", "cc", "aa").hasOnlyUniqueContents()).isFalse()
    }
}
