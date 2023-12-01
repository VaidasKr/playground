package advent.year2022

import advent.assert
import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day20Test {
    @Test
    fun testParse() {
        val values = Day20.fromInput("1\n2\n-3\n3\n-2\n0\n4").values
        Assert.assertEquals(listOf(1, 2, -3, 3, -2, 0, 4), values.toList())
    }

    @Test
    fun test1000after() {
        val day20 = Day20(intArrayOf(1, 2, -3, 4, 0, 3, -2))
        day20.getAfter(0, 1000).assert(4)
        day20.getAfter(0, 2000).assert(-3)
        day20.getAfter(0, 3000).assert(2)
    }

    @Test
    fun testSample() {
        Day20(intArrayOf(1, 2, -3, 3, -2, 0, 4)).perMutate().run {
            Day20.getAfter(this, 0, 1000) + Day20.getAfter(this, 0, 2000) + Day20.getAfter(this, 0, 3000)
        }.assert(3)
    }

    @Test
    fun testSamplePart2() {
        Day20(intArrayOf(1, 2, -3, 3, -2, 0, 4)).perMutateWithKey(811589153).run {
            val after = Day20.getAfter(this, 0, 1000)
            val after1 = Day20.getAfter(this, 0, 2000)
            val after2 = Day20.getAfter(this, 0, 3000)
            println("1000 $after 2000 $after1 3000 $after2")
            after + after1 + after2
        }.assert(1623178306L)
    }

    @Test
    fun testActual() {
        val input = Day20.fromInput(readFile("2022/day20"))
        val mutated = input.perMutate()
        listOf(1000, 2000, 3000).sumOf { indexes ->
            val after = Day20.getAfter(mutated, 0, indexes)
            println("after $indexes -> $after")
            after
        }.assert(4066)
        val mutatedWithKey = input.perMutateWithKey(811589153)
        val result = listOf(1000, 2000, 3000).sumOf { indexes ->
            val after = Day20.getAfter(mutatedWithKey, 0, indexes)
            println("after $indexes -> $after")
            after
        }
        Assert.assertEquals(
            6704537992933,
            result
        )
    }
}
