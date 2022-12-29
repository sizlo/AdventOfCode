package advent.of.code.utils

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CoordinateTest {

    @ParameterizedTest
    @ValueSource(strings = [
        "1,5 -> 1,9 = 4",
        "1,9 -> 1,5 = 4",
        "1,9 -> 1,9 = 0",
        "1,-5 -> 1,9 = 14",
        "1,-5 -> 8,13 = 25",
    ])
    fun `test manhattan distance`(params: String) {
        val match = """(-?\d+,-?\d+) -> (-?\d+,-?\d+) = (-?\d+)""".toRegex().find(params)!!
        val left = Coordinate(match.groupValues[1])
        val right = Coordinate(match.groupValues[2])
        val expected = match.groupValues[3].toInt()
        assertThat(manhattanDistance(left, right)).isEqualTo(expected)
    }
}