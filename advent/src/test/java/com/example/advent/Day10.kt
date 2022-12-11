package com.example.advent

import org.junit.Test

class Day10 {
    @Test
    fun sample() {
        """addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop""".runCycles()
    }

    @Test
    fun part1() {
        """
            noop
            noop
            addx 5
            addx 1
            noop
            noop
            addx 3
            addx 1
            addx 6
            noop
            addx -1
            addx 5
            addx 1
            noop
            addx 4
            addx 1
            noop
            addx -6
            addx 12
            noop
            addx 3
            addx 1
            addx -26
            addx -12
            addx 5
            addx 19
            addx -3
            addx -13
            addx 2
            noop
            addx 3
            addx 2
            noop
            addx 3
            addx 15
            addx -8
            addx 2
            addx 6
            noop
            addx -23
            addx 20
            addx 3
            addx 2
            addx 5
            addx -40
            noop
            noop
            addx 3
            addx 6
            addx -2
            noop
            addx 5
            noop
            noop
            addx 5
            addx -2
            addx 9
            noop
            noop
            noop
            addx -14
            addx 17
            noop
            noop
            addx 8
            noop
            noop
            addx -2
            addx 4
            noop
            noop
            addx -35
            noop
            noop
            noop
            addx -1
            addx 5
            addx 6
            noop
            addx -4
            addx 5
            addx 2
            addx 3
            noop
            addx 5
            addx 14
            addx -10
            addx -25
            addx 1
            addx 38
            addx -6
            addx 2
            addx 3
            addx -2
            addx -38
            noop
            addx 9
            addx -4
            noop
            addx 25
            addx -20
            noop
            addx 3
            addx 2
            addx 5
            addx 2
            addx -9
            addx 14
            addx -2
            noop
            noop
            addx 7
            addx 3
            addx -2
            addx 2
            noop
            addx 3
            addx -38
            noop
            addx 7
            noop
            noop
            addx 1
            noop
            addx 3
            addx 1
            noop
            noop
            addx 6
            noop
            addx 4
            addx 1
            noop
            noop
            addx 4
            addx 1
            addx 7
            addx -3
            addx 5
            noop
            noop
            noop
            noop
            noop
            noop
            noop
        """.trimIndent()
            .runCycles()
    }

    private fun String.runCycles() {
        var x = 1
        var signalStrength = 0
        var cycle = 0
        var spritePosition = 0
        val drawLine = CharArray(40)
        split("\n").forEach { rawLine ->
            val line = rawLine.trim()
            if (line.startsWith("addx")) {
                updateAndDrawIfNeeded(drawLine, cycle, spritePosition)
                cycle++
                signalStrength = updateStrength(x, signalStrength, cycle)
                updateAndDrawIfNeeded(drawLine, cycle, spritePosition)
                cycle++
                signalStrength = updateStrength(x, signalStrength, cycle)
                val update = line.substring(5, line.length).toInt()
                spritePosition += update
                x += update
            } else {
                updateAndDrawIfNeeded(drawLine, cycle, spritePosition)
                cycle++
                signalStrength = updateStrength(x, signalStrength, cycle)
            }
        }
        println(signalStrength)
    }

    private fun updateAndDrawIfNeeded(drawLine: CharArray, cycle: Int, spritePosition: Int) {
        val drawPos = cycle % 40
        val drawChar = if (drawPos < spritePosition || drawPos > spritePosition + 2) ' ' else '#'
        drawLine[drawPos] = drawChar
        if (drawPos == 39) println(drawLine)
    }

    private fun updateStrength(x: Int, signalStrength: Int, cycle: Int): Int {
//        println("cycle $cycle x $x")
        return if ((cycle - 20) % 40 == 0) signalStrength + x * cycle else signalStrength
    }
}
