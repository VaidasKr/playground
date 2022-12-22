package advent.year2022

import advent.packInts
import advent.unpackInt1
import advent.unpackInt2

class Day22(val map: Array<CharArray>) {
    var flatState: State = getInitialState()
    var cubeState: State? = null

    fun initCubeState(cubeInfo: List<CubeWallInfo>) {
        cubeState = CubeState(flatState.direction, flatState.position, cubeInfo, cubeInfo.first())
    }

    private fun getInitialState(): FlatState {
        for (y in 0 until map.size) {
            for (x in 0 until map[0].size) {
                if (map[y][x] == TILE) return FlatState(Direction.Right, packInts(x, y))
            }
        }
        throw RuntimeException("no state found")
    }

    fun runCommand(command: Command) {
        flatState = command.update(map, flatState)
        val cube = cubeState
        if (cube != null) {
            cubeState = command.update(map, cube)
        }
    }

    sealed interface Command {
        fun update(map: Array<CharArray>, state: State): State

        data class Move(val steps: Int) : Command {
            override fun update(map: Array<CharArray>, state: State): State = state.move(map, steps)
        }

        object RotateRight : Command {
            override fun update(map: Array<CharArray>, state: State): State = state.right
            override fun toString(): String = "Rotate Right"
        }

        object RotateLeft : Command {
            override fun update(map: Array<CharArray>, state: State): State = state.left
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

    interface State {
        val direction: Direction
        val position: Long
        val score: Int get() = 1000 * (position.y + 1) + 4 * (position.x + 1) + direction.score
        val left: State
        val right: State

        fun move(map: Array<CharArray>, steps: Int): State
    }

    data class FlatState(override val direction: Direction, override val position: Long) : State {
        override val left: State get() = copy(direction = direction.counterClockWise)
        override val right: State get() = copy(direction = direction.clockWise)

        override fun move(map: Array<CharArray>, steps: Int): FlatState {
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
            return FlatState(direction, position)
        }

        private fun outOfBounds(x: Int, y: Int, width: Int, height: Int) = !inBounds(x, y, width, height)
        private fun inBounds(x: Int, y: Int, width: Int, height: Int): Boolean =
            x >= 0 && x < width && y >= 0 && y < height
    }

    data class CubeState(
        override val direction: Direction,
        override val position: Long,
        private val cubeInfo: List<CubeWallInfo>,
        private val wallInfo: CubeWallInfo
    ) : State {
        override val left: State get() = copy(direction = direction.counterClockWise)
        override val right: State get() = copy(direction = direction.clockWise)

        override fun move(map: Array<CharArray>, steps: Int): State {
            var position = position
            var step = 0
            var x: Int
            var y: Int
            var direction = direction
            var newDirection = direction
            var wallInfo = wallInfo
            var newInfo = wallInfo
            while (step < steps) {
                val next = direction.next(position)
                x = next.x
                y = next.y
                wallInfo.onEdge(x, y) { edge ->
                    newInfo = cubeInfo.first { it.side == edge.side }
                    edge.rotation.rotate(wallInfo.range, newInfo.range, direction, x, y) { rotDir, rotX, rotY ->
                        newDirection = rotDir
                        x = rotX
                        y = rotY
                    }
                }
                if (map[y][x] == WALL) {
                    break
                }
                position = packInts(x, y)
                direction = newDirection
                wallInfo = newInfo
                step++
            }
            return CubeState(direction, position, cubeInfo, wallInfo)
        }
    }

    data class CubeWallInfo(
        val side: CubeSide,
        val range: CubeWallRange,
        val edges: CubeEdges
    ) {
        inline fun onEdge(x: Int, y: Int, crossinline onWrap: (CubeEdge) -> Unit) {
            when {
                x < range.xMin -> onWrap(edges.left)
                x > range.xMax -> onWrap(edges.right)
                y < range.yMin -> onWrap(edges.up)
                y > range.yMax -> onWrap(edges.down)
            }
        }
    }

    data class CubeWallRange(val xMin: Int, val xMax: Int, val yMin: Int, val yMax: Int)

    data class CubeEdges(val left: CubeEdge, val up: CubeEdge, val right: CubeEdge, val down: CubeEdge)

    data class CubeEdge(val side: CubeSide, val rotation: EdgeRotation)

    sealed interface EdgeRotation {
        fun rotate(
            oldRange: CubeWallRange,
            newRange: CubeWallRange,
            direction: Direction,
            x: Int,
            y: Int,
            onRotated: (Direction, Int, Int) -> Unit
        )

        object Right : EdgeRotation {
            override fun toString(): String = "Right"

            override fun rotate(
                oldRange: CubeWallRange,
                newRange: CubeWallRange,
                direction: Direction,
                x: Int,
                y: Int,
                onRotated: (Direction, Int, Int) -> Unit
            ) {
                when (direction) {
                    is Direction.Left -> onRotated(Direction.Down, y - oldRange.yMin + newRange.xMin, newRange.yMin)
                    is Direction.Down -> onRotated(Direction.Right, newRange.xMin, newRange.yMax - (x - oldRange.xMin))
                    is Direction.Up -> onRotated(Direction.Left, newRange.xMax, newRange.yMax - (x - oldRange.xMin))
                    is Direction.Right -> onRotated(Direction.Up, y - oldRange.yMin + newRange.xMin, newRange.yMax)
                }
            }
        }

        object Left : EdgeRotation {
            override fun toString(): String = "Left"

            override fun rotate(
                oldRange: CubeWallRange,
                newRange: CubeWallRange,
                direction: Direction,
                x: Int,
                y: Int,
                onRotated: (Direction, Int, Int) -> Unit
            ) {
                when (direction) {
                    is Direction.Left -> onRotated(Direction.Up, newRange.xMax - (y - oldRange.yMin), newRange.yMax)
                    is Direction.Right -> onRotated(Direction.Down, newRange.xMin + oldRange.yMax - y, newRange.yMin)
                    is Direction.Up -> onRotated(Direction.Right, newRange.xMin, newRange.yMin + (x - oldRange.xMin))
                    is Direction.Down -> onRotated(Direction.Left, newRange.xMax, newRange.yMin + (x - oldRange.xMin))
                }
            }
        }

        object UpsideDown : EdgeRotation {
            override fun toString(): String = "UpsideDown"

            override fun rotate(
                oldRange: CubeWallRange,
                newRange: CubeWallRange,
                direction: Direction,
                x: Int,
                y: Int,
                onRotated: (Direction, Int, Int) -> Unit
            ) {
                when (direction) {
                    is Direction.Up -> onRotated(Direction.Down, newRange.xMax - (x - oldRange.xMin), newRange.yMin)
                    is Direction.Right -> onRotated(Direction.Left, newRange.xMax, newRange.yMax - (y - oldRange.yMin))
                    is Direction.Left -> onRotated(Direction.Right, newRange.xMin, newRange.yMax - (y - oldRange.yMin))
                    is Direction.Down -> onRotated(Direction.Up, newRange.xMax - (x - oldRange.xMin), newRange.yMax)
                }
            }
        }

        object None : EdgeRotation {
            override fun toString(): String = "None"

            override fun rotate(
                oldRange: CubeWallRange,
                newRange: CubeWallRange,
                direction: Direction,
                x: Int,
                y: Int,
                onRotated: (Direction, Int, Int) -> Unit
            ) {
                when (direction) {
                    is Direction.Up -> onRotated(Direction.Up, x - oldRange.xMin + newRange.xMin, newRange.yMax)
                    is Direction.Down -> onRotated(Direction.Down, x - oldRange.xMin + newRange.xMin, newRange.yMin)
                    is Direction.Left -> onRotated(Direction.Left, newRange.xMax, y - oldRange.yMin + newRange.yMin)
                    is Direction.Right -> onRotated(Direction.Right, newRange.xMin, y - oldRange.yMin + newRange.yMin)
                }
            }
        }
    }

    sealed interface CubeSide {
        object Front : CubeSide {
            override fun toString(): String = "Front"
        }

        object Top : CubeSide {
            override fun toString(): String = "Top"
        }

        object Down : CubeSide {
            override fun toString(): String = "Down"
        }

        object Right : CubeSide {
            override fun toString(): String = "Right"
        }

        object Left : CubeSide {
            override fun toString(): String = "Left"
        }

        object Back : CubeSide {
            override fun toString(): String = "Back"
        }
    }

    companion object {
        private const val EMPTY = ' '
        private const val TILE = '.'
        private const val WALL = '#'
    }
}

private val Long.x get() = unpackInt1(this)
private val Long.y get() = unpackInt2(this)
