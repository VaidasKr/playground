package advent.year2015

object Day1 {
    fun getFloor(input: String): Int {
        var counter = 0
        input.forEachIndexed { i, char ->
            when (char) {
                '(' -> counter++
                ')' -> counter--
            }
            if (counter < 0) return i + 1
        }
        return counter
    }
}
