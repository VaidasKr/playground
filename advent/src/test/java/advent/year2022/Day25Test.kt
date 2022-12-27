package advent.year2022

import advent.assert
import advent.fileLines
import advent.print
import org.junit.Test

class Day25Test {
    @Test
    fun `parse to`() {
        Day25.toSnafu(1).assert("1")
        Day25.toSnafu(2).assert("2")
        Day25.toSnafu(3).assert("1=")
        Day25.toSnafu(4).assert("1-")
        Day25.toSnafu(5).assert("10")
        Day25.toSnafu(6).assert("11")
        Day25.toSnafu(7).assert("12")
        Day25.toSnafu(8).assert("2=")
        Day25.toSnafu(9).assert("2-")
        Day25.toSnafu(10).assert("20")
        Day25.toSnafu(15).assert("1=0")
        Day25.toSnafu(19).assert("1--")
        Day25.toSnafu(20).assert("1-0")
        Day25.toSnafu(2022).assert("1=11-2")
        Day25.toSnafu(12345).assert("1-0---0")
        Day25.toSnafu(314159265).assert("1121-1110-1=0")
    }

    @Test
    fun `parse from`() {
        Day25.fromSnafu("1").assert(1)
        Day25.fromSnafu("2").assert(2)
        Day25.fromSnafu("1=").assert(3)
        Day25.fromSnafu("1-").assert(4)
        Day25.fromSnafu("10").assert(5)
        Day25.fromSnafu("11").assert(6)
        Day25.fromSnafu("12").assert(7)
        Day25.fromSnafu("2=").assert(8)
        Day25.fromSnafu("2-").assert(9)
        Day25.fromSnafu("20").assert(10)
        Day25.fromSnafu("1=0").assert(15)
        Day25.fromSnafu("1-0").assert(20)
        Day25.fromSnafu("1=11-2").assert(2022)
        Day25.fromSnafu("1-0---0").assert(12345)
        Day25.fromSnafu("1121-1110-1=0").assert(314159265)
    }

    @Test
    fun sample() {
        val sum = fileLines("2022/day25sample").filter { it.isNotBlank() }.sumOf { Day25.fromSnafu(it) }
        sum.assert(4890)
        Day25.toSnafu(sum).assert("2=-1=0")
    }

    @Test
    fun actual() {
        val sum = fileLines("2022/day25").filter { it.isNotBlank() }.sumOf { Day25.fromSnafu(it) }
        sum.assert(29698499442451)
        Day25.toSnafu(sum).assert("2=-0=1-0012-=-2=0=01")
    }
}
