package advent.of.code.day21

import advent.of.code.utils.readInputLines

private const val SPACES_ON_BOARD = 10

private fun Int.nextTurn() = (this + 1) % 2

class DiracDice {

    class DeterministicDie {
        var totalNumberOfRolls = 0
        private val maxRoll = 100

        fun roll(): Int {
            val result = totalNumberOfRolls % maxRoll + 1
            totalNumberOfRolls++
            return result
        }
    }

    data class PlayerState(val boardPositionMinusOne: Int, val score: Int = 0) {
        fun takeTurnWithDeterministicDie(die: DeterministicDie): PlayerState {
            return moveXSpaces(die.roll() + die.roll() + die.roll())
        }

        fun moveXSpaces(spaces: Int): PlayerState {
            val newBoardPositionMinusOne = (boardPositionMinusOne + spaces) % SPACES_ON_BOARD
            return PlayerState(
                boardPositionMinusOne = newBoardPositionMinusOne,
                score = this.score + newBoardPositionMinusOne + 1
            )
        }
    }

    data class GameState(val playerStates: List<PlayerState>, val turn: Int = 0)

    fun multiplyLosingScoreWithNumberOfDiceRolls(input: List<String>): Int {
        val die = DeterministicDie()
        val players = parseInput(input).toMutableList()

        playGameWithDeterministicDie(players, die)

        return players.minOf { it.score } * die.totalNumberOfRolls
    }

    private fun parseInput(input: List<String>): List<PlayerState> {
        return input
            .mapNotNull { "starting position: (\\d+)".toRegex().find(it)?.groups?.get(1)?.value?.toInt() }
            .map { PlayerState(boardPositionMinusOne = it - 1) }
    }

    private fun playGameWithDeterministicDie(players: MutableList<PlayerState>, die: DeterministicDie) {
        val winningScore = 1000

        var turn = 0
        while (players.maxOf { it.score } < winningScore) {
            players[turn] = players[turn].takeTurnWithDeterministicDie(die)
            turn = turn.nextTurn()
        }
    }

    fun findHowManyUniversesOverallWinnerWinsIn(input: List<String>): Long {
        val players = parseInput(input)
        val playerWins = players.map { 0L }.toMutableList()
        val gameStatesAndCounts = mutableMapOf(GameState(players, 0) to 1L)
        val winningScore = 21

        while (gameStatesAndCounts.isNotEmpty()) {
            val thisState = gameStatesAndCounts.keys.first()
            val thisStateCount = gameStatesAndCounts.remove(thisState)!!

            (1 .. 3).forEach { roll1 ->
                (1 .. 3).forEach { roll2 ->
                    (1 .. 3).forEach { roll3 ->

                        val newPlayerStates = thisState.playerStates.mapIndexed { index, playerState ->
                            moveIfPlayersTurn(index, thisState.turn, playerState, roll1 + roll2 + roll3)
                        }

                        if (newPlayerStates[thisState.turn].score >= winningScore) {
                            playerWins[thisState.turn] = playerWins[thisState.turn] + thisStateCount
                        } else {
                            val newGameState = GameState(newPlayerStates, thisState.turn.nextTurn())
                            gameStatesAndCounts[newGameState] = gameStatesAndCounts.getOrDefault(newGameState, 0) + thisStateCount
                        }
                    }
                }
            }
        }

        return playerWins.maxOf { it }
    }

    private fun moveIfPlayersTurn(playerIndex: Int, turn: Int, playerState: PlayerState, spaces: Int): PlayerState {
        return if (playerIndex == turn) {
            playerState.moveXSpaces(spaces)
        } else {
            playerState
        }
    }
}

fun main() {
    val input = readInputLines("/day21/input.txt")
    println(DiracDice().multiplyLosingScoreWithNumberOfDiceRolls(input)) // 989352
    println(DiracDice().findHowManyUniversesOverallWinnerWinsIn(input)) // 430229563871565
}