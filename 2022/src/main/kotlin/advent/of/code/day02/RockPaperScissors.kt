package advent.of.code.day02

import advent.of.code.utils.Part
import advent.of.code.utils.invert
import advent.of.code.utils.readInputLines

enum class Move(val score: Int, vararg val symbols: Char) {
    ROCK(1, 'A', 'X'), PAPER(2, 'B', 'Y'), SCISSORS(3, 'C', 'Z');

    companion object {
        fun fromSymbol(symbol: Char) = values().first { it.symbols.contains(symbol) }
    }
}

enum class Result(val score: Int, val symbol: Char) {
    LOSS(0, 'X'), DRAW(3, 'Y'), WIN(6, 'Z');

    companion object {
        fun fromSymbol(symbol: Char) = Result.values().first { it.symbol == symbol }
    }
}

abstract class Game(protected val opponentsMove: Move) {
    companion object {
        fun fromString(input: String, part: Part): Game {
            return if (part == Part.PART_1) {
                Part1Game(Move.fromSymbol(input.first()), Move.fromSymbol(input.last()))
            } else {
                Part2Game(Move.fromSymbol(input.first()), Result.fromSymbol(input.last()))
            }
        }
    }

    protected val winCondition = mapOf(
        Move.ROCK to Move.SCISSORS,
        Move.PAPER to Move.ROCK,
        Move.SCISSORS to Move.PAPER
    )

    protected val lossCondition = winCondition.invert()

    fun calculateScore() = findYourMove().score + findResult().score

    abstract fun findYourMove(): Move
    abstract fun findResult(): Result
}

class Part1Game(opponentsMove: Move, private val yourMove: Move): Game(opponentsMove) {
    override fun findYourMove() = yourMove

    override fun findResult(): Result {
        return when (opponentsMove) {
            yourMove -> Result.DRAW
            winCondition[yourMove] -> Result.WIN
            else -> Result.LOSS
        }
    }
}

class Part2Game(opponentsMove: Move, private val result: Result): Game(opponentsMove) {
    override fun findYourMove(): Move {
        return when (result) {
            Result.DRAW -> opponentsMove
            Result.LOSS -> winCondition.getValue(opponentsMove)
            Result.WIN -> lossCondition.getValue(opponentsMove)
        }
    }
    override fun findResult() = result
}

data class StrategyGuide(val games: List<Game>) {
    companion object {
        fun fromStrings(input: List<String>, part: Part) = StrategyGuide(input.map { Game.fromString(it, part) })
    }

    fun calculateTotalScore(): Int {
        return games.sumOf { it.calculateScore() }
    }
}

fun main() {
    println(StrategyGuide.fromStrings(readInputLines("/day02/input.txt"), Part.PART_1).calculateTotalScore()) // 9241
    println(StrategyGuide.fromStrings(readInputLines("/day02/input.txt"), Part.PART_2).calculateTotalScore()) // 14610
}
