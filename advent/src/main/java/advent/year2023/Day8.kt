package advent.year2023

object Day8 {
    fun stepsFrom(input: String, start: String, end: String): Int {
        val lines = input.trim().split('\n')

        val instructions = lines[0].trim()

        val map = buildMap {
            for (i in 2 until lines.size) {
                val line = lines[i]
                val starting = line.substring(0, line.indexOf(' '))
                val leftRight = line.substring(line.indexOf('(') + 1, line.indexOf(')')).split(", ")
                put(starting, leftRight[0] to leftRight[1])
            }
        }


        var i = 0
        var next: String = start
        while (true) {
            val direction = instructions.charAtMod(i++)
            next = getNext(map, next, direction)
            if (next == end) {
                return i
            }
        }
    }

    private fun String.charAtMod(index: Long): Char = get((index % length).toInt())

    private fun String.charAtMod(index: Int): Char = get(index % length)

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

        val nextPoints = mutableListOf<String>()
        val map = buildMap {
            for (i in 2 until lines.size) {
                val line = lines[i]
                val starting = line.substring(0, line.indexOf(' '))
                if (starting.endsWith('A')) {
                    nextPoints.add(starting)
                }
                val leftRight = line.substring(line.indexOf('(') + 1, line.indexOf(')')).split(", ")
                put(starting, leftRight[0] to leftRight[1])
            }
        }

        var i = 0L
        val indices = nextPoints.indices
        val repeating = Array(nextPoints.size) { -1L to -1L }
        while (true) {
            val direction = instructions.charAtMod(i++)
            var isEnd = true
            for (j in indices) {
                nextPoints[j] = getNext(map, nextPoints[j], direction)
                val isEndFor = nextPoints[j].endsWith('Z')
                if (isEndFor) {
                    val (lastIndex, _) = repeating[j]
                    if (lastIndex == -1L) {
                        repeating[j] = i to -1
                    } else {
                        repeating[j] = i to i - lastIndex
                    }
                    if (repeating.all { it.second > -1L }) {
                        return leastCommonMultipleOf(LongArray(repeating.size) { repeating[it].second })
                    }
                }
                isEnd = isEnd && isEndFor
            }
            if (isEnd) return i
        }
    }

    private fun leastCommonMultipleOf(multiples: LongArray): Long {
        var result = multiples[0]
        for (i in 1 until multiples.size) {
            result = lowestCommonMultiple(result, multiples[i])
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
