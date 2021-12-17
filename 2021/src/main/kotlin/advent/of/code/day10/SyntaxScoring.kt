package advent.of.code.day10

import advent.of.code.utils.readInputLines
import java.lang.RuntimeException
import java.util.*

enum class ChunkCharacter(
    private val char: Char,
    val isOpening: Boolean,
    val illegalCharacterScore: Int,
    val completionCharacterScore: Int
) {
    OPEN_ROUND('(', true, 0, 0),
    CLOSE_ROUND(')', false, 3, 1),
    OPEN_SQUARE('[', true, 0, 0),
    CLOSE_SQUARE(']', false, 57, 2),
    OPEN_CURLY('{', true, 0, 0),
    CLOSE_CURLY('}', false, 1197, 3),
    OPEN_POINTY('<', true, 0, 0),
    CLOSE_POINTY('>', false, 25137, 4);

    companion object {
        fun fromChar(char: Char): ChunkCharacter = values().single { char == it.char }
    }

    fun getClosingCharacter(): ChunkCharacter {
        return when (this) {
            OPEN_ROUND -> CLOSE_ROUND
            OPEN_SQUARE -> CLOSE_SQUARE
            OPEN_CURLY -> CLOSE_CURLY
            OPEN_POINTY -> CLOSE_POINTY
            else -> throw RuntimeException("getClosingCharacter() must be called on an opening character")
        }
    }
}

private fun List<ChunkCharacter>.getCompletionStringScore(): Long {
    return this
        .toList()
        .fold(0) { total, char -> (total * 5) + char.completionCharacterScore }
}

class SyntaxScoring {

    class LineProcessor {

        data class LineProcessorResult(
            val firstIllegalCharacter: ChunkCharacter?,
            val completionString: List<ChunkCharacter>?
        )

        fun process(line: String): LineProcessorResult {
            val chunkCharacters = line.toList().map { ChunkCharacter.fromChar(it) }
            val unclosedChunks = chunkCharacters.fold(Stack<ChunkCharacter>()) { stack, char ->
                if (char.isOpening) {
                    stack.push(char)
                } else {
                    val lastOpeningChar = stack.pop()
                    if (char != lastOpeningChar.getClosingCharacter()) {
                        return LineProcessorResult(char, null)
                    }
                }
                return@fold stack
            }

            val completionString = unclosedChunks
                .reversed()
                .map { it.getClosingCharacter() }

            return LineProcessorResult(null, completionString)
        }
    }

    private val lineProcessor = LineProcessor()

    fun findTotalScoreOfCorruptLines(input: List<String>): Int {
        return input
            .map { lineProcessor.process(it) }
            .mapNotNull { it.firstIllegalCharacter }
            .sumOf { it.illegalCharacterScore }
    }

    fun findMiddleScoreOfIncompleteLines(input: List<String>): Long {
        val sortedCompletionStringScores = input
            .map { lineProcessor.process(it) }
            .mapNotNull { it.completionString }
            .map { it.getCompletionStringScore() }
            .sorted()
        return sortedCompletionStringScores[sortedCompletionStringScores.size / 2]
    }
}

fun main() {
    val input = readInputLines("/day10/input.txt")
    val syntaxScoring = SyntaxScoring()
    println(syntaxScoring.findTotalScoreOfCorruptLines(input)) // 388713
    println(syntaxScoring.findMiddleScoreOfIncompleteLines(input)) // 3539961434
}