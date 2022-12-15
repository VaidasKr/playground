package advent.year2022

object Day12 {
    fun shortestDistanceFromStoE(input: String): Int {
        var start = Position(0, 0)
        val map = input.replace(" ", "").split("\n")
            .mapIndexed { indexY, line ->
                line.mapIndexed { indexX, char ->
                    when (char) {
                        'S' -> {
                            start = Position(indexX, indexY)
                            'a'
                        }
                        else -> char
                    }
                }
            }
        return shortestPath(map, start)
    }

    fun shortestDistanceFromAnyAtoE(input: String): Int {
        val starts = mutableListOf<Position>()
        val map = input.replace(" ", "").split("\n")
            .mapIndexed { indexY, line ->
                line.mapIndexed { indexX, char ->
                    val value = when (char) {
                        'S' -> 'a'
                        else -> char
                    }
                    if (value == 'a') starts.add(Position(indexX, indexY))
                    value
                }
            }
        return starts.minOf { start -> shortestPath(map, start) }
    }

    private fun shortestPath(
        map: List<List<Char>>,
        start: Position
    ): Int {
        val height = map.size
        val width = map.first().size
        val visitedPositions = hashSetOf(start)
        var ongoingPositions = hashSetOf(start)
        var steps = 0
        while (ongoingPositions.isNotEmpty()) {
            val newOngoing = hashSetOf<Position>()
            ongoingPositions.forEach { position ->
                val charAt = map[position.y][position.x]
                position.onEdge(width, height) { edge ->
                    val edgeValue = map[edge.y][edge.x]
                    if (edgeValue == 'E' && charAt >= 'y') return steps + 1
                    if (edgeValue - charAt <= 1 && visitedPositions.add(edge)) newOngoing.add(edge)
                }
            }
            ongoingPositions = newOngoing
            steps++
        }
        return Int.MAX_VALUE
    }

    data class Position(val x: Int, val y: Int) {
        inline fun onEdge(width: Int, height: Int, onEdge: (Position) -> Unit) {
            if (x > 0) onEdge(copy(x = x - 1))
            if (x < width - 1) onEdge(copy(x = x + 1))
            if (y > 0) onEdge(copy(y = y - 1))
            if (y < height - 1) onEdge(copy(y = y + 1))
        }
    }
}
