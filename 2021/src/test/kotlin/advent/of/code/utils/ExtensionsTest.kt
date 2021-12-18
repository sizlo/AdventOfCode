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
}