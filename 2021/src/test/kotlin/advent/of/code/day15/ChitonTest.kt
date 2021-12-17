package advent.of.code.day15

import advent.of.code.utils.Part.PART_1
import advent.of.code.utils.Part.PART_2
import advent.of.code.utils.toIntGrid
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ChitonTest {

    private val input = listOf(
        "1163751742",
        "1381373672",
        "2136511328",
        "3694931569",
        "7463417111",
        "1319128137",
        "1359912421",
        "3125421639",
        "1293138521",
        "2311944581",
    ).toIntGrid()

    @Test
    fun `test example input for part 1`() {
        val result = Chiton(PART_1).findLowestPathRisk(input)

        assertThat(result).isEqualTo(40)
    }

    @Test
    fun `test example input for part 2`() {
        val result = Chiton(PART_2).findLowestPathRisk(input)

        assertThat(result).isEqualTo(315)
    }

    @Nested
    inner class TileTest {
        @Test
        fun `test increment tile`() {
            val tile = listOf(
                listOf(1, 2, 3),
                listOf(4, 5, 6),
                listOf(7, 8, 9),
            )

            val expectedTile = listOf(
                listOf(2, 3, 4),
                listOf(5, 6, 7),
                listOf(8, 9, 1),
            )

            assertThat(tile.incrementTile()).isEqualTo(expectedTile)
        }

        @Test
        fun `test join right`() {
            val tile1 = listOf(
                listOf(1, 2, 3),
                listOf(4, 5, 6),
                listOf(7, 8, 9),
            )

            val tile2 = listOf(
                listOf(2, 3, 4),
                listOf(5, 6, 7),
                listOf(8, 9, 1),
            )

            val tile3 = listOf(
                listOf(9, 8, 7),
                listOf(6, 5, 4),
                listOf(3, 2, 1),
            )

            val expectedTile = listOf(
                listOf(1, 2, 3, 2, 3, 4, 9, 8, 7),
                listOf(4, 5, 6, 5, 6, 7, 6, 5, 4),
                listOf(7, 8, 9, 8, 9, 1, 3, 2, 1)
            )

            assertThat(tile1.joinRight(tile2).joinRight(tile3)).isEqualTo(expectedTile)
        }

        @Test
        fun `test join bottom`() {
            val tile1 = listOf(
                listOf(1, 2, 3),
                listOf(4, 5, 6),
                listOf(7, 8, 9),
            )

            val tile2 = listOf(
                listOf(2, 3, 4),
                listOf(5, 6, 7),
                listOf(8, 9, 1),
            )

            val tile3 = listOf(
                listOf(9, 8, 7),
                listOf(6, 5, 4),
                listOf(3, 2, 1),
            )

            val expectedTile = listOf(
                listOf(1, 2, 3),
                listOf(4, 5, 6),
                listOf(7, 8, 9),
                listOf(2, 3, 4),
                listOf(5, 6, 7),
                listOf(8, 9, 1),
                listOf(9, 8, 7),
                listOf(6, 5, 4),
                listOf(3, 2, 1),
            )

            assertThat(tile1.joinBottom(tile2).joinBottom(tile3)).isEqualTo(expectedTile)
        }
    }
}