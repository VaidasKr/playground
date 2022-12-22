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
            day22.currentState.score.assert(6032)
        }
    }

    @Test
    fun sampleWithOverlaps() {
        parseFile("2022/day22sample") { day22, commands ->
            val startState = day22.currentState
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
        currentState = state
        onDay(this)
    }

    private fun Day22.assert(score: Int) {
        currentState.score.assert(score)
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
        val position = day22.currentState.position
        val posX = unpackInt1(position)
        val posY = unpackInt2(position)
        print("  ")
        for (i in 0 until day22.map.first().size) {
            print(i % 10)
        }
        println()
        val posChar = when (day22.currentState.direction) {
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
            day22.currentState.score.print()
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
