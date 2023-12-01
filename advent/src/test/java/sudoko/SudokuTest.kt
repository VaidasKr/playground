package sudoko

import advent.assert
import advent.assertTrue
import org.junit.Assert
import org.junit.Test

class SudokuTest {

    private val input = """
                [["5","3",".",".","7",".",".",".","."],["6",".",".","1","9","5",".",".","."],[".","9","8",".",".",".",".","6","."],["8",".",".",".","6",".",".",".","3"],["4",".",".","8",".","3",".",".","1"],["7",".",".",".","2",".",".",".","6"],[".","6",".",".",".",".","2","8","."],[".",".",".","4","1","9",".",".","5"],[".",".",".",".","8",".",".","7","9"]]
            """.trimIndent()

    private val hardInput =
        """[[".",".","2",".","9","4","5",".","."],[".",".",".","1",".",".","8","2","."],["3",".",".",".",".","7",".","4","."],["1",".",".","7",".",".",".",".","2"],[".",".",".",".","5","1",".",".","4"],["6",".",".",".","4",".",".","8","5"],[".",".","7",".",".","2",".","9","."],[".",".",".",".",".",".",".",".","."],[".",".","4",".",".",".",".",".","3"]]"""

    @Test
    fun actual() {
        Sudoku.solve(input).assert(
            """
                [["5","3","4","6","7","8","9","1","2"],["6","7","2","1","9","5","3","4","8"],["1","9","8","3","4","2","5","6","7"],["8","5","9","7","6","1","4","2","3"],["4","2","6","8","5","3","7","9","1"],["7","1","3","9","2","4","8","5","6"],["9","6","1","5","3","7","2","8","4"],["2","8","7","4","1","9","6","3","5"],["3","4","5","2","8","6","1","7","9"]]
            """.trimIndent()
        )
    }

    @Test
    fun `parse-format`() {
        val actual = Sudoku.parse(input).toString()

        Assert.assertEquals(input, actual)
    }

    @Test
    fun checkInRow() {
        val table = Sudoku.parse(input)
        val numbersInRows = arrayOf(
            intArrayOf(5, 3, 7),
            intArrayOf(6, 1, 9, 5),
            intArrayOf(9, 8, 6),

            intArrayOf(8, 6, 3),
            intArrayOf(4, 8, 3, 1),
            intArrayOf(7, 2, 6),

            intArrayOf(6, 2, 8),
            intArrayOf(4, 1, 9, 5),
            intArrayOf(8, 7, 9),
        )
        for (column in 0..8) {
            val inRow = numbersInRows[column]
            for (row in 0..8) {
                val index = row + 9 * column
                for (number in 1..9) {
                    val result = inRow.contains(number)
                    Assert.assertEquals(result, table.isInRowAt(index, number))
                }
            }
        }
    }

    @Test
    fun checkInColumn() {
        val table = Sudoku.parse(input)
        val numbersInColumn = arrayOf(
            intArrayOf(5, 6, 8, 4, 7),
            intArrayOf(3, 9, 6),
            intArrayOf(8),
            intArrayOf(1, 8, 4),
            intArrayOf(7, 9, 6, 2, 1, 8),
            intArrayOf(5, 3, 9),
            intArrayOf(2),
            intArrayOf(6, 8, 7),
            intArrayOf(3, 1, 6, 5, 9)
        )
        for (row in 0..8) {
            val inColumn = numbersInColumn[row]
            for (column in 0..8) {
                val index = row + 9 * column
                for (number in 1..9) {
                    val result = inColumn.contains(number)
                    Assert.assertEquals(result, table.isInColumnAt(index, number))
                }
            }
        }
    }

    @Test
    fun checkInSquare() {
        val table = Sudoku.parse(input)
        val squareOffset = intArrayOf(0, 3, 6, 27, 30, 33, 54, 57, 60)
        val squareIndexed = intArrayOf(0, 1, 2, 9, 10, 11, 18, 19, 20)
        val numbersInSquares = arrayOf(
            intArrayOf(5, 3, 6, 9, 8),
            intArrayOf(1, 7, 9, 5),
            intArrayOf(6),

            intArrayOf(8, 4, 7),
            intArrayOf(8, 6, 3, 2),
            intArrayOf(3, 1, 6),

            intArrayOf(6),
            intArrayOf(4, 1, 9, 8),
            intArrayOf(2, 8, 5, 7, 9)
        )
        numbersInSquares.forEachIndexed { squareIndex, inSquare ->
            val offset = squareOffset[squareIndex]
            squareIndexed.forEach { i ->
                val index = i + offset
                for (number in 1..9) {
                    val result = inSquare.contains(number)
                    Assert.assertEquals(result, table.isInSquareAt(index, number))
                }
            }
        }
    }

    @Test
    fun checkIteration() {
        Sudoku.parse(hardInput).iterate().assertTrue()
    }

    @Test
    fun `write-squares`() {
        Sudoku.parse(hardInput).apply {
            print()
            for (i in 1..9) {
                println("writing $i")
                println()
                if (writeSquares(i)) {
                    print()
                }
            }
        }
    }
}
