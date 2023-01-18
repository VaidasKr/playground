package advent.year2015

import advent.assert
import advent.fileLines
import org.junit.Test

class Day17Test {
    @Test
    fun sample() {
        val containers = listOf(20, 15, 10, 5, 5)
        Day17.combinationCount(25, containers).assert(4)
        Day17.minCombinationCount(25, containers).assert(3)
    }

    @Test
    fun actual() {
        val containers = fileLines("2015/Day17").filter { it.isNotBlank() }.map { it.toInt() }.toList()
        Day17.combinationCount(150, containers).assert(1638)
        Day17.minCombinationCount(150, containers).assert(17)
    }
}
