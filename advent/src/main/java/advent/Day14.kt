package advent

class Day14 {
    private var minX = 500
    private var maxX = 500
    private var minY = 0
    private var maxY = 0

    private val rocks = hashSetOf<Position>()
    private val sand = hashSetOf<Position>()

    private data class Position(val x: Int, val y: Int) {
        val offers
            get() = sequence {
                yield(Position(x, y + 1))
                yield(Position(x - 1, y + 1))
                yield(Position(x + 1, y + 1))
            }
    }

    fun addLines(lines: List<String>) {
        rocks.clear()
        rocks.addAll(collectPoints(lines))
    }

    fun dropWithFloor(): Boolean {
        var position = Position(500, 0)
        var next = position.offers.firstOrNull { !isOccupied(it) }
        val bottom = maxY + 2
        while (next != null) {
            position = next
            next = position.offers.firstOrNull { !isOccupied(it) && it.y < bottom }
        }
        return sand.add(position)
    }

    fun dropNoFloor(): Boolean {
        var position = Position(500, 0)
        var next = position.offers.firstOrNull { !isOccupied(it) }
        while (next != null) {
            if (position.y == maxY) {
                sand.add(position)
                return false
            }
            position = next
            next = position.offers.firstOrNull { !isOccupied(it) }
        }
        return sand.add(position)
    }

    private fun isOccupied(position: Position): Boolean = rocks.contains(position) || sand.contains(position)

    private fun collectPoints(lines: List<String>): HashSet<Position> {
        val points = HashSet<Position>()
        lines.forEach { line ->
            val linePositions = line.split(" -> ").map { posString ->
                val (x, y) = posString.split(",")
                val xInt = x.toInt()
                val yInt = y.toInt()
                minX = minX.coerceAtMost(xInt)
                minY = minY.coerceAtMost(yInt)
                maxX = maxX.coerceAtLeast(xInt)
                maxY = maxY.coerceAtLeast(yInt)
                Position(xInt, yInt)
            }
            var i = 0
            while (i < linePositions.lastIndex) {
                val from = linePositions[i++]
                val to = linePositions[i]
                val horizontalLine = from.x != to.x
                val verticalLine = from.y != to.y
                if (horizontalLine && verticalLine) throw IllegalArgumentException("diagonal lines")
                if (horizontalLine) {
                    for (x in from.x.coerceAtMost(to.x)..from.x.coerceAtLeast(to.x)) {
                        points.add(Position(x, from.y))
                    }
                } else if (verticalLine) {
                    for (y in from.y.coerceAtMost(to.y)..from.y.coerceAtLeast(to.y)) {
                        points.add(Position(from.x, y))
                    }
                } else {
                    println("no line ?")
                }
            }
        }
        return points
    }

    fun draw(withFloor: Boolean = false) {
        var adjustedMinX = minX
        var adjustedMaxX = maxX
        sand.forEach { (x, _) ->
            adjustedMinX = x.coerceAtMost(adjustedMinX)
            adjustedMaxX = x.coerceAtLeast(adjustedMaxX)
        }
        adjustedMaxX++
        adjustedMinX--
        for (y in minY..maxY) {
            for (x in adjustedMinX..adjustedMaxX) {
                printAtPosition(x, y)
            }
            println()
        }
        if (withFloor) {
            for (x in adjustedMinX..adjustedMaxX) printAtPosition(x, maxY + 1)
            println()
            for (x in adjustedMinX..adjustedMaxX) print('#')
        }
    }

    private fun printAtPosition(x: Int, y: Int) {
        if (x == 500 && y == 0) {
            print('+')
        } else if (rocks.contains(Position(x, y))) {
            print('#')
        } else if (sand.contains(Position(x, y))) {
            print('o')
        } else {
            print(Typography.bullet)
        }
    }
}
