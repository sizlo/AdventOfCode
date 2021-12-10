package advent.of.code.day10

import advent.of.code.day10.SyntaxScoring.LineProcessor
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class SyntaxScoringTest {

    private val exampleInput = listOf(
        "[({(<(())[]>[[{[]{<()<>>",
        "[(()[<>])]({[<{<<[]>>(",
        "{([(<{}[<>[]}>{[]{[(<()>",
        "(((({<>}<{<{<>}{[]{[]{}",
        "[[<[([]))<([[{}[[()]]]",
        "[{[{({}]{}}([{[{{{}}([]",
        "{<[[]]>}<{[{[{[]{()[[[]",
        "[<(<(<(<{}))><([]([]()",
        "<{([([[(<>()){}]>(<<{{",
        "<{([{{}}[<[[[<>{}]]]>[]]",
    )

    private val testSubject = SyntaxScoring()

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.findTotalScoreOfCorruptLines(exampleInput)

        assertThat(result).isEqualTo(26397)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.findMiddleScoreOfIncompleteLines(exampleInput)

        assertThat(result).isEqualTo(288957)
    }

    @Nested
    inner class LineProcessorTest {

        private val testSubject = LineProcessor()

        @ParameterizedTest
        @CsvSource(
            "{([(<{}[<>[]}>{[]{[(<()>,}",
            "[[<[([]))<([[{}[[()]]],)",
            "[{[{({}]{}}([{[{{{}}([],]",
            "[<(<(<(<{}))><([]([](),)",
            "<{([([[(<>()){}]>(<<{{,>",
        )
        fun `test corrupt examples`(line: String, illegalCharacter: String) {
            val expectedResult = ChunkCharacter.fromChar(illegalCharacter.toList().single())

            val result = testSubject.process(line).firstIllegalCharacter

            assertThat(result).isEqualTo(expectedResult)
        }

        @ParameterizedTest
        @CsvSource(
            "[({(<(())[]>[[{[]{<()<>>,}}]])})]",
            "[(()[<>])]({[<{<<[]>>(,)}>]})",
            "(((({<>}<{<{<>}{[]{[]{},}}>}>))))",
            "{<[[]]>}<{[{[{[]{()[[[],]]}}]}]}>",
            "<{([{{}}[<[[[<>{}]]]>[]],])}>",
        )
        fun `test incomplete examples`(line: String, completionString: String) {
            val expectedCompletionString = completionString.toList().map { ChunkCharacter.fromChar(it) }

            val result = testSubject.process(line).completionString

            assertThat(result).isEqualTo(expectedCompletionString)
        }
    }
}