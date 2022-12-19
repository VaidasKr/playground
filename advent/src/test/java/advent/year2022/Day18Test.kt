package advent.year2022

import advent.assert
import advent.print
import advent.readFile
import org.junit.Test

class Day18Test {
    @Test
    fun tests() {
        calculateArea("1,1,1", 6)
        calculateArea("1,2,1", 6)
        calculateArea("1,2,1\n1,1,1", 10)
        calculateArea("1,3,1\n1,1,1", 12)
    }

    @Test
    fun sample() {
        val input = "2,2,2\n1,2,2\n3,2,2\n2,1,2\n2,3,2\n2,2,1\n2,2,3\n2,2,4\n2,2,6\n1,2,5\n3,2,5\n2,1,5\n2,3,5"
        Day18.calculateTotalArea(input).assert(64)
        Day18.calculateOutsideArea(input).assert(58)
    }

    @Test
    fun actual() {
        printArea(readFile("2022/day18"))
    }

    private fun calculateArea(input: String, expected: Long) {
        Day18.calculateTotalArea(input).assert(expected)
    }

    private fun printArea(input: String) {
        Day18.calculateTotalArea(input).print()
    }
}
