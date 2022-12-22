package advent.year2022

import advent.packInts
import advent.unpackInt1
import advent.unpackInt2

class Day22(val map: Array<CharArray>) {
    var currentState = getInitialState()

    private fun getInitialState(): State {
        for (y in 0 until map.size) {
            for (x in 0 until map[0].size) {
                if (map[y][x] == TILE) return State(Direction.Right, packInts(x, y))
            }
        }
        throw RuntimeException("no state found")
    }

    fun runCommand(command: Command) {
        currentState = command.update(map, currentState)
    }

    sealed interface Command {
        fun update(map: Array<CharArray>, state: State): State

        data class Move(val steps: Int) : Command {
            override fun update(map: Array<CharArray>, state: State): State = state.move(map, steps)
        }

        object RotateRight : Command {
            override fun update(map: Array<CharArray>, state: State): State = state.rotateRight()
            override fun toString(): String = "Rotate Right"
        }

        object RotateLeft : Command {
            override fun update(map: Array<CharArray>, state: State): State = state.rotateLeft()
            override fun toString(): String = "Rotate Left"
        }
    }

    sealed interface Direction {
        val clockWise: Direction
        val counterClockWise: Direction
        val opposite: Direction
        val score: Int

        fun next(currentPosition: Long): Long

        object Right : Direction {
            override val clockWise: Direction get() = Down
            override val counterClockWise: Direction get() = Up
            override val opposite: Direction get() = Left
            override val score: Int = 0
            override fun next(currentPosition: Long): Long = packInts(currentPosition.x + 1, currentPosition.y)
            override fun toString(): String = "Right"
        }

        object Left : Direction {
            override val clockWise: Direction get() = Up
            override val counterClockWise: Direction get() = Down
            override val opposite: Direction get() = Right
            override val score: Int = 2
            override fun next(currentPosition: Long): Long = packInts(currentPosition.x - 1, currentPosition.y)
            override fun toString(): String = "Left"
        }

        object Up : Direction {
            override val clockWise: Direction get() = Right
            override val counterClockWise: Direction get() = Left
            override val opposite: Direction get() = Down
            override val score: Int = 3
            override fun next(currentPosition: Long): Long = packInts(currentPosition.x, currentPosition.y - 1)
            override fun toString(): String = "Up"
        }

        object Down : Direction {
            override val clockWise: Direction get() = Left
            override val counterClockWise: Direction get() = Right
            override val opposite: Direction get() = Up
            override val score: Int = 1
            override fun next(currentPosition: Long): Long = packInts(currentPosition.x, currentPosition.y + 1)
            override fun toString(): String = "Down"
        }
    }

    data class State(val direction: Direction, val position: Long) {
        val score: Int get() = 1000 * (position.y + 1) + 4 * (position.x + 1) + direction.score

        fun stateStatus(): String = "State x=${position.x} y=${position.y} looking $direction"

        fun move(map: Array<CharArray>, steps: Int): State {
            var position = position
            var step = 0
            val height = map.size
            val width = map[0].size
            var x: Int
            var y: Int
            while (step < steps) {
                val next = direction.next(position)
                x = next.x
                y = next.y
                val outOfBounds = outOfBounds(x, y, width, height)
                if (outOfBounds || map[y][x] == EMPTY) {
                    val oppositeDirection = direction.opposite
                    var oppositePos = position
                    while (true) {
                        val nextOpposite = oppositeDirection.next(oppositePos)
                        val y1 = nextOpposite.y
                        val x1 = nextOpposite.x
                        if (inBounds(x1, y1, width, height) && map[y1][x1] != EMPTY) {
                            oppositePos = nextOpposite
                        } else {
                            break
                        }
                    }
                    x = oppositePos.x
                    y = oppositePos.y
                }
                if (map[y][x] == WALL) break
                position = packInts(x, y)
                step++
            }
            return State(direction, position)
        }

        private fun outOfBounds(x: Int, y: Int, width: Int, height: Int) = !inBounds(x, y, width, height)
        private fun inBounds(x: Int, y: Int, width: Int, height: Int): Boolean =
            x >= 0 && x < width && y >= 0 && y < height

        fun rotateLeft(): State = copy(direction = direction.counterClockWise)
        fun rotateRight(): State = copy(direction = direction.clockWise)
    }

    companion object {
        private const val EMPTY = ' '
        private const val TILE = '.'
        private const val WALL = '#'
    }
}

private val Long.x get() = unpackInt1(this)
private val Long.y get() = unpackInt2(this)
