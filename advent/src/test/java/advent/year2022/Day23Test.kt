package advent.year2022

import advent.assert
import advent.readFile
import org.junit.Test

class Day23Test {
    @Test
    fun `sample 1`() {
        val day23 = getWithFile("2022/day23sample1")
        var counter = 1
        while (day23.move()) {
            counter++
        }
        counter.assert(4)
    }

    @Test
    fun `sample 2`() {
        val day23 = getWithFile("2022/day23sample2")
        repeat(10) {
            day23.move()
        }
        day23.score().assert(110)
    }

    @Test
    fun `sample 2 part 2`() {
        val day23 = getWithFile("2022/day23sample2")
        var counter = 1
        while (day23.move()) {
            counter++
        }
        counter.assert(20)
    }

    @Test
    fun actual() {
        val day23 = getWithFile("2022/day23")
        repeat(10) {
            day23.move()
        }
        day23.score().assert(4005)
    }

    @Test
    fun `actual part 2`() {
        val day23 = getWithFile("2022/day23")
        var counter = 1
        while (day23.move()) {
            counter++
        }
        counter.assert(1008)
    }

    private fun getWithFile(name: String): Day23 = Day23(readFile(name))
}
