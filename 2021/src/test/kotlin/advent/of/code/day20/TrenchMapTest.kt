package advent.of.code.day20

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class TrenchMapTest {

    private val exampleInput = listOf(
        "..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#",
        "",
        "#..#.",
        "#....",
        "##..#",
        "..#..",
        "..###",
    )

    private val testSubject = TrenchMap()

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.countLitPixelsAfterNIterations(exampleInput, iterations = 2)

        assertThat(result).isEqualTo(35)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.countLitPixelsAfterNIterations(exampleInput, iterations = 50)

        assertThat(result).isEqualTo(3351)
    }
}