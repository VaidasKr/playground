package advent.year2023

object Day3 {
    private const val spaceChar = '.'

    fun findEnginePartNumberSum(input: String): Long {
        val map = input.trim().split('\n')

        var sum = 0L

        for (y in map.indices) {
            val line = map[y]
            for (x in line.indices) {
                val char = line[x]
                if (char.isDigit() || char == spaceChar) {
                    continue
                } else {
                    sum += getNumberSumAround(map, x, y)
                }
            }
        }

        return sum
    }

    private fun getNumberSumAround(map: List<String>, x: Int, y: Int): Long {
        val numbersLeftOf = readNumberToTheLeft(map[y], x - 1)
        val numbersRightOf = readNumberToTheRight(map[y], x + 1)
        var sum = numbersLeftOf + numbersRightOf
        if (y > 0) {
            sum += readNumbersInLineOfCenter(map[y - 1], x)
        }
        if (y < map.size - 1) {
            sum += readNumbersInLineOfCenter(map[y + 1], x)
        }
        return sum
    }

    private fun readNumberToTheLeft(line: String, x: Int): Long {
        var index = x
        var multiplier = 1L
        var sum = 0L
        while (index >= 0 && line[index].isDigit()) {
            sum += multiplier * line[index].digitToInt()
            multiplier *= 10
            index--
        }
        return sum
    }

    private fun readNumberToTheRight(line: String, x: Int): Long {
        if (x >= line.length || !line[x].isDigit()) return 0
        var startIndex = x
        while (startIndex < line.length && line[startIndex].isDigit()) {
            startIndex++
        }

        return readNumberToTheLeft(line, startIndex - 1)
    }

    private fun readNumbersInLineOfCenter(line: String, x: Int): Long {
        if (line[x] == spaceChar) {
            return readNumberToTheLeft(line, x - 1) + readNumberToTheRight(line, x + 1)
        }
        if (x > 0 && line[x - 1] == spaceChar) {
            return readNumberToTheRight(line, x)
        }
        if (x < line.length - 1 && line[x + 1] == spaceChar) {
            return readNumberToTheLeft(line, x)
        }
        return readNumberToTheRight(line, x - 1)
    }

    fun findGearRatioSum(input: String): Long {
        val map = input.trim().split('\n')

        var sum = 0L

        for (y in map.indices) {
            val line = map[y]
            for (x in line.indices) {
                val char = line[x]
                if (char == '*') {
                    sum += getGearRatioSum(map, x, y)
                }
            }
        }

        return sum
    }

    private fun getGearRatioSum(map: List<String>, x: Int, y: Int): Long {
        val numbers = mutableListOf(readNumberToTheLeft(map[y], x - 1), readNumberToTheRight(map[y], x + 1))
        if (y > 0) {
            numbers.addAll(getNumbersInLineOfCenter(map[y - 1], x))
        }
        if (y < map.size - 1) {
            numbers.addAll(getNumbersInLineOfCenter(map[y + 1], x))
        }
        val filtered = numbers.filter { it > 0 }
        return if (filtered.size == 2) filtered[0] * filtered[1] else 0
    }

    private fun getNumbersInLineOfCenter(line: String, x: Int): List<Long> {
        if (line[x] == spaceChar) {
            return listOf(readNumberToTheLeft(line, x - 1), readNumberToTheRight(line, x + 1))
        }
        if (x > 0 && line[x - 1] == spaceChar) {
            return listOf(readNumberToTheRight(line, x))
        }
        if (x < line.length - 1 && line[x + 1] == spaceChar) {
            return listOf(readNumberToTheLeft(line, x))
        }
        return listOf(readNumberToTheRight(line, x - 1))
    }
}
