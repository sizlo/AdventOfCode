package advent.of.code.utils

import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Nested
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
    fun `test triangle-ing a number`() {
        val five = 5
        assertThat(five.triangle()).isEqualTo(15)
    }

    @Test
    fun `test checking a number is triangle`() {
        val one = 1
        val two = 2
        val three = 3
        val four = 4
        val five = 5
        val six = 6

        assertThat(one.isTriangle()).isTrue()
        assertThat(two.isTriangle()).isFalse()
        assertThat(three.isTriangle()).isTrue()
        assertThat(four.isTriangle()).isFalse()
        assertThat(five.isTriangle()).isFalse()
        assertThat(six.isTriangle()).isTrue()
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
    fun `test int range intersect`() {
        assertThat((1..5).getIntersectWithOther(6 .. 7)).isNull()
        assertThat((3 .. 5).getIntersectWithOther(1 .. 2)).isNull()
        assertThat((3 .. 5).getIntersectWithOther(1 .. 7)).isEqualTo(3 .. 5)
        assertThat((1 .. 7).getIntersectWithOther(3 .. 5)).isEqualTo(3 .. 5)
        assertThat((1 .. 4).getIntersectWithOther(3 .. 4)).isEqualTo(3 .. 4)
        assertThat((3 .. 5).getIntersectWithOther(4 .. 6)).isEqualTo(4 .. 5)
    }

    @Test
    fun `test int range contains other int range`() {
        val container = 5 .. 10

        assertThat(container.containsOther(6 .. 8)).isTrue()
        assertThat(container.containsOther(5 .. 10)).isTrue()

        assertThat(container.containsOther(1 .. 3)).isFalse()
        assertThat(container.containsOther(4 .. 5)).isFalse()
        assertThat(container.containsOther(4 .. 6)).isFalse()

        assertThat(container.containsOther(12 .. 13)).isFalse()
        assertThat(container.containsOther(10 .. 11)).isFalse()
        assertThat(container.containsOther(10 .. 12)).isFalse()
    }

    @Nested
    inner class TestSplitIntRange {

        @Test
        fun `test split with total overlap`() {
            val result = (3 .. 5).splitBasedOnOverlapWith(1 .. 7)
            assertThat(result).containsExactlyInAnyOrder(
                3 .. 5
            )
        }

        @Test
        fun `test split with no overlap (too low)`() {
            val result = (3 .. 5).splitBasedOnOverlapWith(0 .. 2)
            assertThat(result).containsExactlyInAnyOrder(
                3 .. 5
            )
        }

        @Test
        fun `test split with no overlap (too high)`() {
            val result = (3 .. 5).splitBasedOnOverlapWith(6 .. 7)
            assertThat(result).containsExactlyInAnyOrder(
                3 .. 5
            )
        }

        @Test
        fun `test split with entirely contained overlap`() {
            val result = (1 .. 5).splitBasedOnOverlapWith(3 .. 4)
            assertThat(result).containsExactlyInAnyOrder(
                1 .. 2,
                3 .. 4,
                5 .. 5
            )
        }

        @Test
        fun `test split with partial overlap (lower)`() {
            val result = (3 .. 5).splitBasedOnOverlapWith(1 .. 4)
            assertThat(result).containsExactlyInAnyOrder(
                3 .. 4,
                5 .. 5
            )
        }

        @Test
        fun `test split with partial overlap (higher)`() {
            val result = (3 .. 5).splitBasedOnOverlapWith(5 .. 7)
            assertThat(result).containsExactlyInAnyOrder(
                3 .. 4,
                5 .. 5
            )
        }
    }
}