package advent.year2022

class Day24(input: String) {
    private val mapState: MapState
    private val start: Point
    private val end: Point

    init {
        val lines = input.split("\n")
        val blizzards = mutableListOf<Blizzard>()
        for (y in lines.indices) {
            val line = lines[y]
            for (x in line.indices) {
                line[x].onBlizzard { offset -> blizzards.add(Blizzard(Point(x, y), offset)) }
            }
        }
        start = Point(1, 0)
        end = Point(lines.first().length - 2, lines.size - 1)
        mapState = MapState(0, blizzards, Point(1, 1), Point(lines.first().length - 2, lines.size - 2))
    }

    private inline fun Char.onBlizzard(offset: (Point) -> Unit) {
        when (this) {
            '>' -> offset(Point(1, 0))
            '<' -> offset(Point(-1, 0))
            'v' -> offset(Point(0, 1))
            '^' -> offset(Point(0, -1))
        }
    }

    private data class Point(val x: Int, val y: Int) {
        operator fun plus(other: Point): Point = Point(x + other.x, y + other.y)
    }

    private data class Blizzard(val point: Point, val offset: Point)

    private data class MapState(val time: Int, val blizzards: List<Blizzard>, val min: Point, val max: Point) {
        var blizzardPoints = hashSetOf<Point>()

        init {
            blizzards.forEach { blizzard -> blizzardPoints.add(blizzard.point) }
        }

        fun update(): MapState {
            val blizzards =
                blizzards.map { blizzard -> Blizzard(wrap(blizzard.point + blizzard.offset), blizzard.offset) }
            return MapState(time + 1, blizzards, min, max)
        }

        private fun wrap(point: Point): Point {
            if (point.x > max.x) return Point(min.x, point.y)
            if (point.x < min.x) return Point(max.x, point.y)
            if (point.y > max.y) return Point(point.x, min.y)
            if (point.y < min.y) return Point(point.x, max.y)
            return point
        }

        fun isValid(option: Point): Boolean =
            option.x >= min.x && option.x <= max.x &&
                option.y >= min.y && option.y <= max.y &&
                !blizzardPoints.contains(option)
    }

    fun timeFromStartToFinish(): Int = timeFromTo(mapState, start, end).time

    fun timeFromStartToFinishAndBack(): Int =
        timeFromTo(timeFromTo(timeFromTo(mapState, start, end), end, start), start, end).time

    private fun timeFromTo(mapState: MapState, start: Point, end: Point): MapState {
        var map = mapState
        var ongoing = hashSetOf(TimePoint(mapState.time, start))
        while (ongoing.isNotEmpty()) {
            map = map.update()
            val newOngoing = hashSetOf<TimePoint>()
            ongoing.forEach { timePoint ->
                timePoint.options() { option ->
                    if (option == end) return map
                    if (option == start || map.isValid(option)) {
                        newOngoing.add(TimePoint(map.time, option))
                    }
                }
            }
            ongoing = newOngoing
        }
        throw RuntimeException("could not find path")
    }

    private data class TimePoint(val time: Int, val point: Point) {
        inline fun options(onOption: (Point) -> Unit) {
            onOption(point)
            onOption(Point(point.x - 1, point.y))
            onOption(Point(point.x + 1, point.y))
            onOption(Point(point.x, point.y - 1))
            onOption(Point(point.x, point.y + 1))
        }
    }
}
