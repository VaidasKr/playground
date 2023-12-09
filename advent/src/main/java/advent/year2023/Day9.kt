package advent.year2023

object Day9 {
    fun sumOfNextNumbers(lines: String): Long = lines.split('\n').sumOf(::predictNextNumber)

    fun predictNextNumber(line: String): Long = predictNext(lineToLong(line)) { array, dif -> array.last() + dif }

    private fun lineToLong(line: String): LongArray = line.split(' ').run { LongArray(size) { this[it].toLong() } }

    fun sumOfPrevNumbers(lines: String): Long = lines.split('\n').sumOf(::predictPrevNumber)

    fun predictPrevNumber(line: String): Long = predictNext(lineToLong(line)) { array, dif -> array.first() - dif }

    private fun predictNext(array: LongArray, operation: (LongArray, Long) -> Long): Long {
        if (array.all { it == 0L }) return 0
        val dif = LongArray(array.size - 1) { array[it + 1] - array[it] }
        return operation(array, predictNext(dif, operation))
    }
}
