package advent.year2015

object Day2 {
    fun calculateWrappingPaper(input: String): Long {
        var sum = 0L
        val buffer = IntArray(3)
        input.split("\n").forEach {
            it.split("x").forEachIndexed { index, s -> buffer[index] = s.toInt() }
            buffer.sort()
            sum += buffer[0] * buffer[1]
            for (i in 0..2) {
                sum += 2 * buffer[i] * buffer[(i + 1) % 3]
            }
        }
        return sum
    }

    fun calculateRibbon(input: String): Long {
        var sum = 0L
        val buffer = IntArray(3)
        input.split("\n").forEach {
            it.split("x").forEachIndexed { index, s -> buffer[index] = s.toInt() }
            buffer.sort()
            sum += 2 * buffer[0] + 2 * buffer[1] + buffer[0] * buffer[1] * buffer[2]
        }
        return sum
    }
}
