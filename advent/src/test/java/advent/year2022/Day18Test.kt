package advent.year2022

import advent.assert
import advent.readFile
import org.junit.Assert
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
        Day18.calculateTotalWithCubes(input).assert(64)
        Day18.print(input)
        Day18.calculateOutside(input).assert(58)
    }

    @Test
    fun actual() {
        val input = readFile("2022/day18")
        val result1 = Day18.calculateTotalWithCubes(input)
        Assert.assertEquals(3374, result1)
        val result2 = Day18.calculateOutside(input)
        Assert.assertEquals(2010, result2)
    }

    private fun calculateArea(input: String, expected: Long) {
        Day18.calculateTotalWithCubes(input).assert(expected)
    }
}
