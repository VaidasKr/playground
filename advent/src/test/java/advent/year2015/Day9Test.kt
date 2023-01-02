package advent.year2015

import advent.assert
import advent.readFile
import org.junit.Test

class Day9Test {
    @Test
    fun sample() {
        val input = """
            London to Dublin = 464
            London to Belfast = 518
            Dublin to Belfast = 141
        """.trimIndent()
        val day9 = Day9(input)
        day9.shortestToVisitAll().assert(605)
        day9.longestPathToVisitAll().assert(982)
    }

    @Test
    fun actual() {
        val day9 = Day9(readFile("2015/Day9"))
        day9.shortestToVisitAll().assert(117)
        day9.longestPathToVisitAll().assert(909)
    }
}
