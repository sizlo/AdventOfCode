package advent.of.code.day04

import advent.of.code.utils.readInput
import advent.of.code.utils.toIntList
import kotlin.math.sqrt

data class Cell(val value: Int, var marked: Boolean = false)

typealias RowOrColumn = List<Cell>
fun RowOrColumn.isFullyMarked(): Boolean {
    return this.none { !it.marked }
}

class Board(val cells: List<Cell>) {

    private val gridSize = sqrt(cells.size.toDouble()).toInt()

    fun getAllRowsAndColumns(): List<RowOrColumn> {
        val rows = cells.chunked(gridSize)
        val columns = (0 until gridSize).map { firstIndex ->
            val lastIndex = (gridSize * (gridSize - 1)) + firstIndex
            cells.slice(firstIndex..lastIndex step gridSize)
        }
        return rows.union(columns).toList()
    }

    fun markCellsWithValue(value: Int) {
        cells
            .filter { it.value == value }
            .forEach { it.marked = true }
    }

    fun isVictorious(): Boolean {
        return getAllRowsAndColumns().any { it.isFullyMarked() }
    }

    fun calculateUnmarkedCellScore(): Int {
        return cells.filter { !it.marked }.sumOf { it.value }
    }
}

class GiantSquid {

    fun findWinningScore(inputLines: List<String>): Int {
        return findAllScores(inputLines).first()
    }

    fun findLosingScore(inputLines: List<String>): Int {
        return findAllScores(inputLines).last()
    }

    private fun findAllScores(inputLines: List<String>): List<Int> {
        val (drawnNumbers, boards) = parseInput(inputLines)
        return playBingo(drawnNumbers, boards)
    }

    private fun parseInput(inputLines: List<String>): Pair<List<Int>, List<Board>> {
        val drawnNumbers = inputLines[0].split(",").toIntList()
        val boardLines = inputLines.subList(2, inputLines.size)
        val boards = parseBoards(boardLines)
        return Pair(drawnNumbers, boards)
    }

    fun parseBoards(boardLines: List<String>): List<Board> {
        val numSeparators = boardLines.count { it.isBlank() }
        val numBoards = numSeparators + 1
        val linesPerBoard = (boardLines.size - numSeparators) / numBoards
        return boardLines
            .filter { it.isNotBlank() }
            .chunked(linesPerBoard)
            .map(::parseBoard)
    }

    private fun parseBoard(boardLines: List<String>): Board {
        val cells = boardLines
            .flatMap { it.split("\\s".toRegex()) }
            .filter { it.isNotBlank() }
            .map { Cell(it.toInt()) }
        return Board(cells)
    }

    private fun playBingo(drawnNumbers: List<Int>, boards: List<Board>): List<Int> {
        val scores = mutableListOf<Int>()
        for (drawnNumber in drawnNumbers) {
            val boardsStillPlaying = boards.filter { !it.isVictorious() }
            boardsStillPlaying
                .onEach { it.markCellsWithValue(drawnNumber) }
                .filter { it.isVictorious() }
                .map { it.calculateUnmarkedCellScore() * drawnNumber }
                .forEach { scores.add(it) }
        }
        return scores
    }
}

fun main() {
    val input = readInput("/day04/input.txt")
    val giantSquid = GiantSquid()
    println(giantSquid.findWinningScore(input)) // 6592
    println(giantSquid.findLosingScore(input)) // 31755
}