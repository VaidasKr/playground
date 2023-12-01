package advent.year2022

import advent.assert
import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day17Test {
    @Test
    fun sample() {
        val day17 = Day17(">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")
        day17.runWithBuffer(2022, 100).assert(3068)
    }

    @Test
    fun actual() {
        val day17 = Day17(readFile("2022/day17"))
        day17.runWithBuffer(10022, 100, false).assert(15478)
        day17.runWithBuffer(10022, 100, true).assert(15478)
        val result = day17.runWithBuffer(1_000_000_000_000L, 100, true)
        Assert.assertEquals(1542941176480, result)
    }
}
