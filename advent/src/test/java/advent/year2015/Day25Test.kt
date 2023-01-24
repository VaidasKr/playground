package advent.year2015

import advent.assert
import advent.print
import org.junit.Test

class Day25Test {
    @Test
    fun sample() {
        val results = listOf(
            20151125, 18749137, 17289845, 30943339, 10071777, 33511524,
            31916031, 21629792, 16929656, 7726640, 15514188, 4041754,
            16080970, 8057251, 1601130, 7981243, 11661866, 16474243,
            24592653, 32451966, 21345942, 9380097, 10600672, 31527494,
            77061, 17552253, 28094349, 6899651, 9250759, 31663883,
            33071741, 6796745, 25397450, 24659492, 1534922, 27995004,
        )
        for (row in 1..6) {
            for (column in 1..6) {
                val expected = results[(row - 1) * 6 + column - 1].toLong()
                Day25.calculate(row, column).assert(expected)
            }
        }
    }

    @Test
    fun actual() {
        Day25.calculate(2978, column = 3083).print()
    }

    @Test
    fun operations() {
        val sequence = listOf(
            20151125,
            31916031,
            18749137,
            16080970,
            21629792,
            17289845,
            24592653,
            8057251,
            16929656,
            30943339,
            77061,
            32451966,
            1601130,
            7726640,
            10071777,
            33071741,
            17552253,
            21345942,
            7981243,
            15514188,
            33511524
        )
        for (i in 0 until sequence.lastIndex) {
            Day25.next(sequence[i].toLong()).assert(sequence[i + 1].toLong())
        }
    }
}
