package advent.year2022

import advent.assert
import advent.print
import advent.readFile
import advent.unpackInt1
import advent.unpackInt2
import org.junit.Test

class Day22Test {
    @Test
    fun sample() {
        parseFile("2022/day22sample") { day22, commands ->
            commands.forEach { command -> day22.runCommand(command) }
            day22.flatState.score.assert(6032)
        }
    }

    @Test
    fun sampleCube() {
        parseFile("2022/day22sample") { day22, commands ->
            val wallInfoMap = buildList {
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Front,
                        Day22.CubeWallRange(8, 11, 0, 3),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Left, Day22.EdgeRotation.Right),
                            up = Day22.CubeEdge(Day22.CubeSide.Top, Day22.EdgeRotation.UpsideDown),
                            right = Day22.CubeEdge(Day22.CubeSide.Right, Day22.EdgeRotation.UpsideDown),
                            down = Day22.CubeEdge(Day22.CubeSide.Down, Day22.EdgeRotation.None)
                        )
                    )
                )
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Top,
                        Day22.CubeWallRange(0, 3, 4, 7),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Right, Day22.EdgeRotation.Left),
                            up = Day22.CubeEdge(Day22.CubeSide.Front, Day22.EdgeRotation.UpsideDown),
                            right = Day22.CubeEdge(Day22.CubeSide.Left, Day22.EdgeRotation.None),
                            down = Day22.CubeEdge(Day22.CubeSide.Back, Day22.EdgeRotation.UpsideDown)
                        )
                    )
                )
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Left,
                        Day22.CubeWallRange(4, 7, 4, 7),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Top, Day22.EdgeRotation.None),
                            up = Day22.CubeEdge(Day22.CubeSide.Front, Day22.EdgeRotation.Left),
                            right = Day22.CubeEdge(Day22.CubeSide.Down, Day22.EdgeRotation.None),
                            down = Day22.CubeEdge(Day22.CubeSide.Back, Day22.EdgeRotation.Right)
                        )
                    )
                )
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Down,
                        Day22.CubeWallRange(8, 11, 4, 7),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Left, Day22.EdgeRotation.None),
                            up = Day22.CubeEdge(Day22.CubeSide.Front, Day22.EdgeRotation.None),
                            right = Day22.CubeEdge(Day22.CubeSide.Right, Day22.EdgeRotation.Left),
                            down = Day22.CubeEdge(Day22.CubeSide.Back, Day22.EdgeRotation.None)
                        )
                    )
                )
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Back,
                        Day22.CubeWallRange(8, 11, 8, 11),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Left, Day22.EdgeRotation.Left),
                            up = Day22.CubeEdge(Day22.CubeSide.Down, Day22.EdgeRotation.None),
                            right = Day22.CubeEdge(Day22.CubeSide.Right, Day22.EdgeRotation.None),
                            down = Day22.CubeEdge(Day22.CubeSide.Top, Day22.EdgeRotation.UpsideDown)
                        )
                    )
                )
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Right,
                        Day22.CubeWallRange(12, 15, 8, 11),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Back, Day22.EdgeRotation.None),
                            up = Day22.CubeEdge(Day22.CubeSide.Down, Day22.EdgeRotation.Right),
                            right = Day22.CubeEdge(Day22.CubeSide.Front, Day22.EdgeRotation.UpsideDown),
                            down = Day22.CubeEdge(Day22.CubeSide.Top, Day22.EdgeRotation.Right)
                        )
                    )
                )
            }
            day22.initCubeState(wallInfoMap)
            commands.forEach { command ->
                day22.runCommand(command)
            }
            day22.cubeState!!.score.assert(5031)
        }
    }

    @Test
    fun actualCube() {
        parseFile("2022/day22") { day22, commands ->
            val wallInfoMap = buildList {
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Front,
                        Day22.CubeWallRange(50, 99, 0, 49),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Left, Day22.EdgeRotation.UpsideDown),
                            up = Day22.CubeEdge(Day22.CubeSide.Top, Day22.EdgeRotation.Left),
                            right = Day22.CubeEdge(Day22.CubeSide.Right, Day22.EdgeRotation.None),
                            down = Day22.CubeEdge(Day22.CubeSide.Down, Day22.EdgeRotation.None)
                        )
                    )
                )
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Top,
                        Day22.CubeWallRange(0, 49, 150, 199),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Front, Day22.EdgeRotation.Right),
                            up = Day22.CubeEdge(Day22.CubeSide.Left, Day22.EdgeRotation.None),
                            right = Day22.CubeEdge(Day22.CubeSide.Back, Day22.EdgeRotation.Right),
                            down = Day22.CubeEdge(Day22.CubeSide.Right, Day22.EdgeRotation.None)
                        )
                    )
                )
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Left,
                        Day22.CubeWallRange(0, 49, 100, 149),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Front, Day22.EdgeRotation.UpsideDown),
                            up = Day22.CubeEdge(Day22.CubeSide.Down, Day22.EdgeRotation.Left),
                            right = Day22.CubeEdge(Day22.CubeSide.Back, Day22.EdgeRotation.None),
                            down = Day22.CubeEdge(Day22.CubeSide.Top, Day22.EdgeRotation.None)
                        )
                    )
                )
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Down,
                        Day22.CubeWallRange(50, 99, 50, 99),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Left, Day22.EdgeRotation.Right),
                            up = Day22.CubeEdge(Day22.CubeSide.Front, Day22.EdgeRotation.None),
                            right = Day22.CubeEdge(Day22.CubeSide.Right, Day22.EdgeRotation.Right),
                            down = Day22.CubeEdge(Day22.CubeSide.Back, Day22.EdgeRotation.None)
                        )
                    )
                )
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Back,
                        Day22.CubeWallRange(50, 99, 100, 149),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Left, Day22.EdgeRotation.None),
                            up = Day22.CubeEdge(Day22.CubeSide.Down, Day22.EdgeRotation.None),
                            right = Day22.CubeEdge(Day22.CubeSide.Right, Day22.EdgeRotation.UpsideDown),
                            down = Day22.CubeEdge(Day22.CubeSide.Top, Day22.EdgeRotation.Left)
                        )
                    )
                )
                add(
                    Day22.CubeWallInfo(
                        Day22.CubeSide.Right,
                        Day22.CubeWallRange(100, 149, 0, 49),
                        Day22.CubeEdges(
                            left = Day22.CubeEdge(Day22.CubeSide.Front, Day22.EdgeRotation.None),
                            up = Day22.CubeEdge(Day22.CubeSide.Top, Day22.EdgeRotation.None),
                            right = Day22.CubeEdge(Day22.CubeSide.Back, Day22.EdgeRotation.UpsideDown),
                            down = Day22.CubeEdge(Day22.CubeSide.Down, Day22.EdgeRotation.Left)
                        )
                    )
                )
            }
            day22.initCubeState(wallInfoMap)
            commands.forEach { command ->
                day22.runCommand(command)
            }
            day22.cubeState!!.score.assert(104385)
        }
    }

    @Test
    fun sampleWithOverlaps() {
        parseFile("2022/day22sample") { day22, commands ->
            val startState = day22.flatState
            printMap(day22)
            day22.testFrom(startState) {
                move(1)
                right()
                printMap(day22)
                assert(1041)
            }
            day22.testFrom(startState) {
                move(2)
                right()
                assert(1045)
            }
            day22.testFrom(startState) {
                move(3)
                right()
                assert(1045)
            }
            day22.testFrom(startState) {
                move(1)
                left()
                assert(1043)
            }
            day22.testFrom(startState) {
                move(2)
                left()
                assert(1047)
            }
            day22.testFrom(startState) {
                move(1)
                left()
                move(1)
                left()
                move(1)
                left()
                move(1)
                left()
                assert(1036)
            }
        }
    }

    private fun Day22.testFrom(state: Day22.State, onDay: Day22.() -> Unit) {
        flatState = state
        onDay(this)
    }

    private fun Day22.assert(score: Int) {
        flatState.score.assert(score)
    }

    private fun Day22.right() {
        runCommand(Day22.Command.RotateRight)
    }

    private fun Day22.left() {
        runCommand(Day22.Command.RotateLeft)
    }

    private fun Day22.move(steps: Int) {
        runCommand(Day22.Command.Move(steps))
    }

    private fun printMap(day22: Day22) {
        val position = day22.flatState.position
        val posX = unpackInt1(position)
        val posY = unpackInt2(position)
        print("  ")
        for (i in 0 until day22.map.first().size) {
            print(i % 10)
        }
        println()
        val posChar = when (day22.flatState.direction) {
            Day22.Direction.Down -> 'v'
            Day22.Direction.Left -> '<'
            Day22.Direction.Right -> '>'
            Day22.Direction.Up -> '^'
        }
        day22.map.forEachIndexed { y, chars ->
            print(y % 10)
            print(' ')
            chars.forEachIndexed { x, char ->
                when {
                    x == posX && y == posY -> print(posChar)
                    char == '.' -> print(Typography.bullet)
                    else -> print(char)
                }
            }
            println()
        }
    }

    @Test
    fun actual() {
        parseFile("2022/day22") { day22, commands ->
            commands.forEach { command ->
                day22.runCommand(command)
            }
            day22.flatState.score.print()
        }
    }

    private fun parseFile(name: String, onParsed: (Day22, List<Day22.Command>) -> Unit) {
        val sampleInputLines = readFile(name).split("\n")
        val mapLines = sampleInputLines.dropLast(2)
        val commandLine = sampleInputLines.last()

        val longestLine = mapLines.maxOf { it.length }
        val map = Array(mapLines.size) { index ->
            val charLine = mapLines[index].toCharArray()
            if (charLine.size < longestLine) {
                charLine + CharArray(longestLine - charLine.size) { ' ' }
            } else {
                charLine
            }
        }
        val commands = buildList {
            var number = -1
            commandLine.forEach { char ->
                if (char.isDigit()) {
                    if (number == -1) number = 0
                    number = number * 10 + char.digitToInt()
                } else {
                    if (number != -1) {
                        add(Day22.Command.Move(number))
                        number = -1
                    }
                    if (char == 'R') {
                        add(Day22.Command.RotateRight)
                    }
                    if (char == 'L') {
                        add(Day22.Command.RotateLeft)
                    }
                }
            }
            if (number != -1) {
                add(Day22.Command.Move(number))
            }
        }
        val day22 = Day22(map)
        onParsed(day22, commands)
    }
}
