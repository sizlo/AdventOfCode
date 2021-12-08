package advent.of.code.day08

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

internal class SevenSegmentSearchTest {

    private val exampleInput = listOf(
        "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe",
        "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc",
        "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg",
        "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb",
        "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea",
        "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb",
        "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe",
        "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef",
        "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb",
        "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce",
    )

    private val testSubject = SevenSegmentSearch(exampleInput)

    @Test
    fun `test example input for part 1`() {
        assertThat(testSubject.countOutputSignalPatternsWhichUseAUniqueNumberOfSegments()).isEqualTo(26)
    }

    @Test
    fun `test example input for part 2`() {
        assertThat(testSubject.decodeAndSumAllOutputValues()).isEqualTo(61229)
    }

    @Test
    fun `test smaller example input for part 2`() {
        val smallerExampleInput = listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
        val testSubject = SevenSegmentSearch(smallerExampleInput)

        assertThat(testSubject.decodeAndSumAllOutputValues()).isEqualTo(5353)
    }
}