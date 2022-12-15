package advent.year2022

object Day1 {
    fun part1(input: String): Int {
        var max = 0
        runLines(input) { index ->
            max = max.coerceAtLeast(index)
        }
        return max
    }

    private inline fun runLines(input: String, onBlank: (Int) -> Unit) {
        var indexCalories = 0
        input.split("\n").forEach { line ->
            if (line.isBlank()) {
                onBlank(indexCalories)
                indexCalories = 0
            } else {
                indexCalories += line.trim().toInt()
            }
        }
        onBlank(indexCalories)
    }

    fun part2(input: String): Int {
        val maxes = intArrayOf(0, 0, 0)
        var candidate: Int
        var atPos: Int
        var i: Int
        runLines(input) { new ->
            i = 0
            candidate = new
            while (i < maxes.size) {
                atPos = maxes[i]
                if (candidate > atPos) {
                    maxes[i] = candidate
                    candidate = atPos
                }
                i++
            }
        }
        return maxes.sum()
    }
}
