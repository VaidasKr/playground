package advent.year2015

import advent.assert
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
        result.length.assert(252594)
        repeat(10) {
            result = Day10.transform(result)
        }
        result.length.assert(3579328)
    }

    private fun String.transformAndTest(expect: String) {
        Day10.transform(this).assert(expect)
    }
}
