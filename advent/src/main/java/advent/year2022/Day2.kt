package advent.year2022

object Day2 {
    fun part1(input: String): Int {
        var score = 0
        runLines(input) { line ->
            val you = line[2]
            score += when (line[0]) {
                'A' -> when (you) {
                    'X' -> 1 + 3
                    'Y' -> 2 + 6
                    'Z' -> 3
                    else -> 0
                }
                'B' -> when (you) {
                    'X' -> 1 + 0
                    'Y' -> 2 + 3
                    'Z' -> 3 + 6
                    else -> 0
                }
                'C' -> when (you) {
                    'X' -> 1 + 6
                    'Y' -> 2 + 0
                    'Z' -> 3 + 3
                    else -> 0
                }
                else -> 0
            }
        }
        return score
    }

    private fun runLines(input: String, onLine: (String) -> Unit) {
        input.split("\n").forEach { onLine(it.trim()) }
    }

    fun part2(input: String): Int {
        var score = 0
        runLines(input) { line ->
            val you = line[2]
            score += when (line[0]) {
                'A' -> when (you) {
                    'X' -> 0 + 3
                    'Y' -> 3 + 1
                    'Z' -> 6 + 2
                    else -> 0
                }
                'B' -> when (you) {
                    'X' -> 1 + 0
                    'Y' -> 2 + 3
                    'Z' -> 3 + 6
                    else -> 0
                }
                'C' -> when (you) {
                    'X' -> 2 + 0
                    'Y' -> 3 + 3
                    'Z' -> 1 + 6
                    else -> 0
                }
                else -> 0
            }
        }
        return score
    }
}
