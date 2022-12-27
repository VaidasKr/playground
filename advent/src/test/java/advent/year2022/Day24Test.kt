package advent.year2022

import advent.assert
import advent.print
import advent.readFile
import org.junit.Test

class Day24Test {
    @Test
    fun sample() {
        val day24 = Day24(readFile("2022/day24sample"))
        day24.timeFromStartToFinish().assert(18)
    }

    @Test
    fun actual() {
        val day24 = Day24(readFile("2022/day24"))
        day24.timeFromStartToFinish().assert(260)
    }

    @Test
    fun sample2() {
        val day24 = Day24(readFile("2022/day24sample"))
        day24.timeFromStartToFinishAndBack().assert(54)
    }

    @Test
    fun actual2() {
        val day24 = Day24(readFile("2022/day24"))
        day24.timeFromStartToFinishAndBack().assert(747)
    }
}
