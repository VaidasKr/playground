package advent.year2015

object Day25 {
    const val START = 20151125L

    fun calculate(row: Int, column: Int): Long {
        var r = 1
        var c = 1
        var value = START

        while (r != row || c != column) {
            value = next(value)
            if (r > 1) {
                c++
                r--
            } else {
                r = c + 1
                c = 1
            }
        }
        return value
    }

    fun next(current: Long): Long = current * 252533L % 33554393L
}
