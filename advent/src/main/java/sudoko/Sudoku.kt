package sudoko

@JvmInline
value class Sudoku(val array: IntArray) {
    fun iterate(): Boolean = hasUpdatesInRange(1..9) { number -> write(number) }

    private inline fun hasUpdatesInRange(range: IntRange, updateCheck: (Int) -> Boolean): Boolean {
        var updated = false
        for (number in range) updated = updateCheck(number) || updated
        return updated
    }

    private fun write(number: Int): Boolean = writeSquares(number) || writeRows(number) || writeColumns(number)

    fun writeSquares(number: Int): Boolean = hasUpdatesInRange(0..2) { row ->
        hasUpdatesInRange(0..2) { col -> writeSquare(row * 3, col * 3, number) }
    }

    private fun writeSquare(row: Int, col: Int, number: Int): Boolean {
        if (isInSquare(row, col, number)) return false
        var possibleIndex = -1
        for (r in row..row + 2) {
            for (c in col..col + 2) {
                if (valueAt(r, c) != 0) {
                    continue
                }
                if (!isInRow(r, number) && !isInColumn(c, number)) {
                    if (possibleIndex == -1) {
                        val indexOf = indexOf(r, c)
                        possibleIndex = indexOf
                    } else {
                        return false
                    }
                }
            }
        }
        return if (possibleIndex == -1) {
            false
        } else {
            array[possibleIndex] = number
            true
        }
    }

    private fun writeRows(number: Int): Boolean = hasUpdatesInRange(0..8) { row -> writeRow(row, number) }

    private fun writeRow(row: Int, number: Int): Boolean {
        if (isInRow(row, number)) return false
        var possibleCol = -1
        for (col in 0..8) {
            if (valueAt(row, col) != 0) continue
            if (!isInSquare(row, col, number) && !isInColumn(col, number)) {
                if (possibleCol == -1) {
                    possibleCol = col
                } else {
                    return false
                }
            }
        }
        return if (possibleCol == -1) {
            false
        } else {
            writeAt(row, possibleCol, number)
            true
        }
    }


    private fun writeColumns(number: Int): Boolean = hasUpdatesInRange(0..8) { column -> writeColumn(column, number) }

    private fun writeColumn(column: Int, number: Int): Boolean {
        if (isInColumn(column, number)) return false
        var possibleRow = -1
        for (row in 0..8) {
            if (valueAt(row, column) != 0) continue
            if (!isInSquare(row, column, number) && !isInRow(row, number)) {
                if (possibleRow == -1) {
                    possibleRow = row
                } else {
                    return false
                }
            }
        }
        return if (possibleRow == -1) {
            false
        } else {
            writeAt(possibleRow, column, number)
            true
        }
    }

    fun print() {
        var index = 0
        while (index < 81) {
            val value = array[index++]
            if (value == 0) {
                print('.')
            } else {
                print(value)
            }
            print(' ')

            if (index % 27 == 0) {
                println()
                println("--------------------")
            } else if (index % 9 == 0) {
                println()
            } else if (index % 3 == 0) {
                print('|')
            }
        }
        println()
    }

    override fun toString(): String = buildString {
        append('[')
        var index = 0
        repeatWithSeparator(
            times = 9,
            action = {
                append('[')
                repeatWithSeparator(
                    9,
                    {
                        append('"')
                        val value = array[index++]
                        if (value == 0) {
                            append('.')
                        } else {
                            append(value)
                        }
                        append('"')
                    },
                    {
                        append(',')
                    }
                )
                append(']')
            }, separator = {
                append(',')
            }
        )
        append(']')
    }

    fun isInRowAt(index: Int, value: Int): Boolean = isInRow(rowAtIndex(index), value)

    private fun isInRow(row: Int, value: Int): Boolean {
        for (col in 0..8) {
            if (valueAt(row, col) == value) return true
        }
        return false
    }

    fun isInColumnAt(index: Int, value: Int): Boolean = isInColumn(columnAtIndex(index), value)

    private fun isInColumn(column: Int, value: Int): Boolean {
        for (row in 0..8) {
            if (valueAt(row, column) == value) return true
        }
        return false
    }

    fun isInSquareAt(index: Int, value: Int): Boolean {
        val squareCol = columnAtIndex(index) / 3 * 3
        val squareRow = rowAtIndex(index) / 3 * 3

        for (col in squareCol..squareCol + 2) {
            for (row in squareRow..squareRow + 2) {
                if (valueAt(row, col) == value) return true
            }
        }
        return false
    }

    private fun isInSquare(row: Int, col: Int, value: Int): Boolean = isInSquareAt(indexOf(row, col), value)

    private fun rowAtIndex(index: Int) = index / 9

    private fun columnAtIndex(index: Int) = index % 9

    private fun indexOf(row: Int, col: Int): Int = row * 9 + col

    private fun valueAt(row: Int, col: Int): Int = array[indexOf(row, col)]

    private fun writeAt(row: Int, col: Int, value: Int) {
        array[indexOf(row, col)] = value
    }

    private inline fun repeatWithSeparator(times: Int, action: () -> Unit, separator: () -> Unit) {
        for (i in 2..times) {
            action()
            separator()
        }
        action()
    }

    companion object {
        fun parse(input: String): Sudoku {
            val array = IntArray(81)
            val table = Sudoku(array)
            var index = 0
            input.replace("\"", "").replace("[", "").replace("]", "").split(",").forEach { value ->
                if (value == ".") {
                    array[index++] = 0
                } else {
                    array[index++] = value.toInt()
                }
            }
            return table
        }

        fun solve(input: String): String {
            val table = parse(input)

            while (table.iterate()) {

            }
            return table.toString()
        }
    }
}
