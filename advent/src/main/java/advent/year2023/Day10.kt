package advent.year2023

object Day10 {
    private const val ABOVE = "|7F"
    private const val BELLOW = "|JL"
    private const val LEFT = "-FL"
    private const val RIGHT = "-7J"

    fun findFurthestPoint(input: String): Long {
        val map = input.split('\n').filter { it.isNotBlank() }
        return furthestPathFrom(map, findStart(map))
    }

    private fun findStart(map: List<String>): Pair<Int, Int> {
        for (y in map.indices) {
            val line = map[y]
            for (x in line.indices) {
                val char = map[y][x]
                if (char == 'S') return x to y
            }
        }
        throw IllegalStateException()
    }

    private fun furthestPathFrom(map: List<String>, start: Pair<Int, Int>): Long {
        val visited = buildSet {
            val startX = start.first
            val startY = start.second
            if (startY > 0) {
                if (ABOVE.contains(map[startY - 1][startX])) add(startX to startY - 1)
            }
            if (startY < map.size - 1) {
                if (BELLOW.contains(map[startY + 1][startX])) add(startX to startY + 1)
            }
            val line = map[startY]
            if (startX > 0) {
                if (LEFT.contains(line[startX - 1])) add(startX - 1 to startY)
            }
            if (startX < line.length - 1) {
                if (RIGHT.contains(line[startX + 1])) add(startX + 1 to startY)
            }
        }.toHashSet()
        var points = visited.toSet()
        visited.add(start)
        var steps = 1L
        while (points.size == 2) {
            steps++
            val newPoints = hashSetOf<Pair<Int, Int>>()
            for (point in points) {
                neighbourPoints(map, point) { neighbour ->
                    if (visited.add(neighbour)) {
                        newPoints.add(neighbour)
                    }
                }
            }
            points = newPoints
        }
        return steps
    }

    private inline fun neighbourPoints(map: List<String>, point: Pair<Int, Int>, onPoint: (Pair<Int, Int>) -> Unit) {
        when (map[point.second][point.first]) {
            '-' -> {
                onPoint(point.first - 1 to point.second)
                onPoint(point.first + 1 to point.second)
            }

            '|' -> {
                onPoint(point.first to point.second - 1)
                onPoint(point.first to point.second + 1)
            }

            'F' -> {
                onPoint(point.first + 1 to point.second)
                onPoint(point.first to point.second + 1)
            }

            'J' -> {
                onPoint(point.first - 1 to point.second)
                onPoint(point.first to point.second - 1)
            }

            'L' -> {
                onPoint(point.first + 1 to point.second)
                onPoint(point.first to point.second - 1)
            }

            '7' -> {
                onPoint(point.first - 1 to point.second)
                onPoint(point.first to point.second + 1)
            }
        }
    }

    fun countInsidePoints(input: String): Int {
        val map = input.split('\n').filter { it.isNotBlank() }
        val mutable = map.toMutableList()
        val start = findStart(map)
        val startChar = determineStartChar(map, start)
        mutable[start.second] = mutable[start.second].replace('S', startChar)
        val visited = getPipeLoopPoints(map, start)
        return countInsidePoints(mutable, visited)
    }

    private fun determineStartChar(map: List<String>, start: Pair<Int, Int>): Char {
        val startX = start.first
        val startY = start.second
        val aboveChar = map.getOrElse(startY - 1) { "" }.getOrElse(startX) { '.' }
        val bellowChar = map.getOrElse(startY + 1) { "" }.getOrElse(startX) { '.' }
        val line = map[startY]
        val rightChar = line.getOrElse(startX + 1) { '.' }
        val leftChar = line.getOrElse(startX - 1) { '.' }
        return when {
            ABOVE.contains(aboveChar) -> {
                when {
                    BELLOW.contains(bellowChar) -> '|'
                    RIGHT.contains(rightChar) -> 'L'
                    LEFT.contains(leftChar) -> 'J'
                    else -> throw RuntimeException("fail above")
                }
            }

            BELLOW.contains(bellowChar) -> {
                when {
                    RIGHT.contains(rightChar) -> 'F'
                    LEFT.contains(leftChar) -> '7'
                    else -> throw RuntimeException("fail bellow")
                }
            }

            RIGHT.contains(rightChar) && LEFT.contains(leftChar) -> return '-'

            else -> {
                throw RuntimeException("fail")
            }
        }
    }

    private fun countInsidePoints(map: MutableList<String>, visited: Set<Pair<Int, Int>>): Int {
        val updated = map.mapIndexed { y, line ->
            buildString(line.length) {
                for (x in line.indices) {
                    if (!visited.contains(x to y)) {
                        append('.')
                    } else {
                        append(line[x])
                    }
                }
            }
        }
        var sum = 0
        for (y in updated.indices) {
            var inside = false
            for (x in updated[y].indices) {
                val char = updated[y][x]
                if (char == '.' && inside) {
                    sum++
                } else if (ABOVE.contains(char)) {
                    inside = !inside
                }
            }
        }
        return sum
    }

    private fun getPipeLoopPoints(map: List<String>, start: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val visited = hashSetOf<Pair<Int, Int>>()
        val startX = start.first
        val startY = start.second
        if (startY > 0) {
            if (ABOVE.contains(map[startY - 1][startX])) visited.add(startX to startY - 1)
        }
        if (startY < map.size - 1) {
            if (BELLOW.contains(map[startY + 1][startX])) visited.add(startX to startY + 1)
        }
        val line = map[startY]
        if (startX > 0) {
            if (LEFT.contains(line[startX - 1])) visited.add(startX - 1 to startY)
        }
        if (startX < line.length - 1) {
            if (RIGHT.contains(line[startX + 1])) visited.add(startX + 1 to startY)
        }
        var points = visited.toSet()
        visited.add(start)
        while (points.size == 2) {
            val newPoints = hashSetOf<Pair<Int, Int>>()
            for (point in points) {
                neighbourPoints(map, point) { neighbour ->
                    if (visited.add(neighbour)) {
                        newPoints.add(neighbour)
                    }
                }
            }
            points = newPoints
        }
        return visited
    }
}
