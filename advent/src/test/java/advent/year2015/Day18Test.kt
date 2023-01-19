package advent.year2015

import advent.assert
import advent.readFile
import org.junit.Test

class Day18Test {
    @Test
    fun sample() {
        val input = """
            .#.#.#
            ...##.
            #....#
            ..#...
            #.#..#
            ####..
        """.trimIndent()
        var day = Day18.fromString(input)
        var corner = day.enableCorners()
        repeat(4) {
            day = day.next()
            corner = corner.nextWithCorners()
        }
        day.turnedOnLights.assert(4)
        corner.nextWithCorners().turnedOnLights.assert(17)
    }

    @Test
    fun actual() {
        var state = Day18.fromString(readFile("2015/Day18"))
        var corners = state.enableCorners()
        repeat(100) {
            state = state.next()
            corners = corners.nextWithCorners()
        }
        state.turnedOnLights.assert(768)
        corners.turnedOnLights.assert(781)
    }
}
