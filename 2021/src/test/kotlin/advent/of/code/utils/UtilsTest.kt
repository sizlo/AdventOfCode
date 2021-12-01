package advent.of.code.utils

import assertk.assertThat
import assertk.assertions.containsExactly
import org.junit.jupiter.api.Test

internal class UtilsTest {

    @Test
    fun `test list to int`() {
        val stringList = listOf("01", "203", "-1")

        val intList = stringList.toIntList()

        assertThat(intList).containsExactly(1, 203, -1)
    }

    @Test
    fun `test read input`() {
        val result = readInput("/test.txt")

        assertThat(result).containsExactly("line one", "line two", "line three")
    }
}