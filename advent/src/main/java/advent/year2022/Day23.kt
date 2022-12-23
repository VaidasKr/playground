package advent.year2022

class Day23(input: String) {
    private var positions: Set<Position>
    private var direction: MoveDirection = MoveDirection.North

    init {
        val list = input.split("\n")
        positions = buildSet {
            for (y in list.indices) {
                val line = list[y]
                for (x in line.indices) {
                    if (line[x] == '#') {
                        add(Position(x, y))
                    }
                }
            }
        }
    }

    fun score(): Int {
        var max: Position? = null
        var min: Position? = null
        positions.forEach {
            max = max.max(it)
            min = min.min(it)
        }
        return (max!!.x - min!!.x + 1) * (max!!.y - min!!.y + 1) - positions.size
    }

    private fun printMap(max: Position, min: Position): Pair<Position, Position> {
        for (y in min.y..max.y) {
            for (x in min.x..max.x) {
                if (positions.contains(Position(x, y))) {
                    print('#')
                } else {
                    print(Typography.bullet)
                }
            }
            println()
        }
        return Pair(max, min)
    }

    private fun Position?.max(other: Position): Position = this?.max(other) ?: other

    private fun Position?.min(other: Position): Position = this?.min(other) ?: other

    private fun nextDirections(): List<MoveDirection> {
        val offsets = direction.directions
        direction = direction.next
        return offsets
    }

    fun move(): Boolean {
        val newPositions = hashSetOf<Position>()
        val suggestedMoving = mutableListOf<Move>()
        val suggestedDirectionCounter = hashMapOf<Position, Int>()
        val directions = nextDirections()
        var notMoving = 0
        positions.forEach { position ->
            if (Position.neighbourOffsets.none { offset -> positions.contains(position + offset) }) {
                notMoving++
                newPositions.add(position)
            } else {
                val firstDirection = directions.firstOrNull { direction ->
                    direction.offsets.none { offset -> positions.contains(position + offset) }
                }
                if (firstDirection != null) {
                    val destination = position + firstDirection.offset
                    val counter = suggestedDirectionCounter.getOrDefault(destination, 0)
                    suggestedDirectionCounter[destination] = counter + 1
                    if (counter > 0) {
                        newPositions.add(position)
                        suggestedMoving.removeAll { move ->
                            if (move.to == destination) {
                                newPositions.add(move.from)
                                true
                            } else {
                                false
                            }
                        }
                    } else {
                        suggestedMoving.add(Move(position, destination))
                    }
                } else {
                    newPositions.add(position)
                }
            }
        }
        if (notMoving == positions.size) return false
        suggestedMoving.forEach { move -> newPositions.add(move.to) }
        positions = newPositions
        return true
    }

    private data class Move(val from: Position, val to: Position)

    private sealed interface MoveDirection {
        val next: MoveDirection
        val offsets: List<Position>
        val offset: Position
        val directions: List<MoveDirection>
            get() = buildList {
                var dir = this@MoveDirection
                while (size < 4) {
                    add(dir)
                    dir = dir.next
                }
            }

        object North : MoveDirection {
            override val next: MoveDirection get() = South
            override val offsets: List<Position> get() = Position.northOffsets
            override val offset: Position get() = Position.north
        }

        object South : MoveDirection {
            override val next: MoveDirection get() = West
            override val offsets: List<Position> get() = Position.southOffsets
            override val offset: Position get() = Position.south
        }

        object West : MoveDirection {
            override val next: MoveDirection get() = East
            override val offsets: List<Position> get() = Position.westOffsets
            override val offset: Position get() = Position.west
        }

        object East : MoveDirection {
            override val next: MoveDirection get() = North
            override val offsets: List<Position> get() = Position.eastOffsets
            override val offset: Position get() = Position.east
        }
    }

    private data class Position(val x: Int, val y: Int) {
        fun max(other: Position): Position = Position(x.coerceAtLeast(other.x), y.coerceAtLeast(other.y))

        fun min(other: Position): Position = Position(x.coerceAtMost(other.x), y.coerceAtMost(other.y))

        operator fun plus(other: Position): Position = Position(x + other.x, y + other.y)

        operator fun minus(other: Position): Position = Position(x - other.x, y - other.y)

        companion object {
            val neighbourOffsets: List<Position> =
                listOf(
                    Position(-1, -1), Position(0, -1), Position(1, -1),
                    Position(-1, 0), /*   center    */ Position(1, 0),
                    Position(-1, 1), Position(0, 1), Position(1, 1)
                )
            val north: Position = Position(0, -1)
            val south: Position = Position(0, 1)
            val west: Position = Position(-1, 0)
            val east: Position = Position(1, 0)
            val northOffsets: List<Position> = neighbourOffsets.filter { it.y < 0 }
            val southOffsets: List<Position> = neighbourOffsets.filter { it.y > 0 }
            val westOffsets: List<Position> = neighbourOffsets.filter { it.x < 0 }
            val eastOffsets: List<Position> = neighbourOffsets.filter { it.x > 0 }
        }
    }
}
