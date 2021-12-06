package advent.of.code.day04

import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class GiantSquidTest {

    private val exampleInput = listOf(
        "7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1",
        "",
        "22 13 17 11  0",
        "8  2 23  4 24",
        "21  9 14 16  7",
        "6 10  3 18  5",
        "1 12 20 15 19",
        "",
        "3 15  0  2 22",
        "9 18 13 17  5",
        "19  8  7 25 23",
        "20 11 10 24  4",
        "14 21 16 12  6",
        "",
        "14 21 17 24  4",
        "10 16 15  9 19",
        "18  8 23 26 20",
        "22 11 13  6  5",
        "2  0 12  3  7",
    )

    private val testSubject = GiantSquid()

    @Test
    fun `test example input for part 1`() {
        val result = testSubject.findWinningScore(exampleInput)

        assertThat(result).isEqualTo(4512)
    }

    @Test
    fun `test example input for part 2`() {
        val result = testSubject.findLosingScore(exampleInput)

        assertThat(result).isEqualTo(1924)
    }

    @Test
    fun `test board parsing`() {
        val boardLines = listOf(
            " 1     2 3    ",
            "4 5 6",
            "7 8 9",
            "",
            "9 8 7",
            "6 5 4",
            "3 2 1",
        )

        val result = testSubject.parseBoards(boardLines)

        assertThat(result[0].cells.map { it.value }).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9)
        assertThat(result[1].cells.map { it.value }).containsExactly(9, 8, 7, 6, 5, 4, 3, 2, 1)
    }

    @Nested
    inner class BoardTest {
        private lateinit var testSubject: Board

        @BeforeEach
        fun createFreshBoard() {
            testSubject = Board(listOf(
                Cell(11), Cell(12), Cell(13), Cell(14), Cell(15),
                Cell(21), Cell(22), Cell(23), Cell(24), Cell(25),
                Cell(31), Cell(32), Cell(33), Cell(34), Cell(35),
                Cell(41), Cell(42), Cell(43), Cell(44), Cell(45),
                Cell(51), Cell(52), Cell(53), Cell(54), Cell(55),
            ))
        }

        @Test
        fun `generates correct rows and columns from board`() {
            val result = testSubject.getAllRowsAndColumns()

            assertThat(result).containsExactly(
                listOf(Cell(11), Cell(12), Cell(13), Cell(14), Cell(15)),
                listOf(Cell(21), Cell(22), Cell(23), Cell(24), Cell(25)),
                listOf(Cell(31), Cell(32), Cell(33), Cell(34), Cell(35)),
                listOf(Cell(41), Cell(42), Cell(43), Cell(44), Cell(45)),
                listOf(Cell(51), Cell(52), Cell(53), Cell(54), Cell(55)),
                listOf(Cell(11), Cell(21), Cell(31), Cell(41), Cell(51)),
                listOf(Cell(12), Cell(22), Cell(32), Cell(42), Cell(52)),
                listOf(Cell(13), Cell(23), Cell(33), Cell(43), Cell(53)),
                listOf(Cell(14), Cell(24), Cell(34), Cell(44), Cell(54)),
                listOf(Cell(15), Cell(25), Cell(35), Cell(45), Cell(55)),
            )
        }

        @Test
        fun `marks numbers with correct value`() {
            testSubject.markCellsWithValue(12)
            testSubject.markCellsWithValue(44)
            testSubject.markCellsWithValue(35)

            val result = testSubject.cells.filter { it.marked }.map { it.value }

            assertThat(result).containsExactlyInAnyOrder(12, 44, 35)
        }

        @Test
        fun `returns not victorious when no rows or columns are complete`() {
            // Mark 4 / 5 cells in a column
            testSubject.markCellsWithValue(13)
            testSubject.markCellsWithValue(23)
            testSubject.markCellsWithValue(33)
            testSubject.markCellsWithValue(43)
            // Mark 4 / 5 cells in a row
            testSubject.markCellsWithValue(31)
            testSubject.markCellsWithValue(32)
            testSubject.markCellsWithValue(33)
            testSubject.markCellsWithValue(34)

            val result = testSubject.isVictorious()

            assertThat(result).isFalse()
        }

        @Test
        fun `returns victorious when row or column is complete`() {
            // Mark entire column
            testSubject.markCellsWithValue(13)
            testSubject.markCellsWithValue(23)
            testSubject.markCellsWithValue(33)
            testSubject.markCellsWithValue(43)
            testSubject.markCellsWithValue(53)

            val result = testSubject.isVictorious()

            assertThat(result).isTrue()
        }
    }
}