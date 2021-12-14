package advent.of.code.day14

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class ExtendedPolymerizationTest {

    private val exampleInput = listOf(
        "NNCB",
        "",
        "CH -> B",
        "HH -> N",
        "CB -> H",
        "NH -> C",
        "HB -> C",
        "HC -> B",
        "HN -> C",
        "NN -> C",
        "BH -> H",
        "NC -> B",
        "NB -> B",
        "BN -> B",
        "BB -> N",
        "BC -> B",
        "CC -> N",
        "CN -> C",
    )

    private val testSubject = ExtendedPolymerization()

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.findMostCommonElementMinusLeastCommonElementAfterNSteps(exampleInput, steps = 10)

        assertThat(result).isEqualTo(1588)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.findMostCommonElementMinusLeastCommonElementAfterNSteps(exampleInput, steps = 40)

        assertThat(result).isEqualTo(2188189693529)
    }
}