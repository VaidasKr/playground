package advent.year2023

object Day8 {
    fun stepsFrom(input: String, start: String, end: String): Long {
        val lines = input.trim().split('\n')

        val instructions = lines[0].trim()

        return stepsToMatching(buildMap(lines), instructions, start) { location -> location == end }
    }

    private fun buildMap(lines: List<String>): Map<String, Pair<String, String>> =
        buildMap {
            for (i in 2 until lines.size) {
                val line = lines[i]
                val starting = line.substring(0, line.indexOf(' '))
                val leftRight = line.substring(line.indexOf('(') + 1, line.indexOf(')')).split(", ")
                put(starting, leftRight[0] to leftRight[1])
            }
        }

    private inline fun stepsToMatching(
        map: Map<String, Pair<String, String>>,
        instructions: String,
        start: String,
        end: (String) -> Boolean
    ): Long {
        var step = 0L
        var next: String = start
        while (true) {
            val direction = instructions.charAtMod(step++)
            next = getNext(map, next, direction)
            if (end(next)) return step
        }
    }

    private fun String.charAtMod(index: Long): Char = get((index % length).toInt())

    private fun getNext(
        map: Map<String, Pair<String, String>>,
        next: String,
        direction: Char
    ) = when (direction) {
        'L' -> map[next]!!.first
        'R' -> map[next]!!.second
        else -> throw RuntimeException("illegal char $direction")
    }

    fun simultaneousSteps(input: String): Long {
        val lines = input.trim().split('\n')

        val instructions = lines[0].trim()

        val map = buildMap(lines)
        return map.keys.filter { it.endsWith('A') }
            .map { stepsToMatching(map, instructions, it) { location -> location.endsWith('Z') } }
            .leastCommonMultipleOf()
    }

    private fun List<Long>.leastCommonMultipleOf(): Long {
        var result = this[0]
        for (i in 1 until size) {
            result = lowestCommonMultiple(result, this[i])
        }
        return result
    }

    private fun greatestCommonDivider(a: Long, b: Long): Long {
        var calcA = a
        var calcB = b
        while (calcB > 0) {
            val temp = calcB
            calcB = calcA % calcB
            calcA = temp
        }
        return calcA
    }

    private fun lowestCommonMultiple(a: Long, b: Long): Long = a * (b / greatestCommonDivider(a, b))
}
