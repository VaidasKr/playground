package advent.year2015

import org.junit.Assert
import org.junit.Test

class Day10Test {
    @Test
    fun `simple 1`() {
        "1".transformAndTest("11")
    }

    @Test
    fun `simple 2`() {
        "11".transformAndTest("21")
    }

    @Test
    fun `simple 3`() {
        "21".transformAndTest("1211")
    }

    @Test
    fun `simple 4`() {
        "1211".transformAndTest("111221")
    }

    @Test
    fun `simple 5`() {
        "111221".transformAndTest("312211")
    }

    @Test
    fun actual() {
        var result = "1113222113"
        repeat(40) {
            result = Day10.transform(result)
        }
        Assert.assertEquals(252594, result.length)
        repeat(10) {
            result = Day10.transform(result)
        }
        Assert.assertEquals(3579328, result.length)
    }

    private fun String.transformAndTest(expect: String) {
        Assert.assertEquals(expect, Day10.transform(this))
    }
}
