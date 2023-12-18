package advent.year2023

import kotlin.math.absoluteValue

object Day18 {
    fun calcDigArea(input: String): Long =
        calcDigArea(input) { line -> line.first() to line.substring(2, line.indexOf(' ', 2)).toInt() }

    fun calcDigAreaHex(input: String): Long = calcDigArea(input) { line ->
        val hexPart = line.split(' ')[2].run { substring(2, length - 1) }
        when (val dirNum = hexPart.last().digitToInt()) {
            0 -> 'R' to hexPart.take(5).toInt(16)
            1 -> 'D' to hexPart.take(5).toInt(16)
            2 -> 'L' to hexPart.take(5).toInt(16)
            3 -> 'U' to hexPart.take(5).toInt(16)
            else -> error("unsupported dir $dirNum")
        }
    }

    private fun calcDigArea(input: String, map: (String) -> Pair<Char, Int>): Long {
        val lines = input.split('\n')
        var x1 = 0L
        var y1 = 0L
        var area = 0L
        var outline = 0
        for (i in lines.indices) {
            val (dir, amount) = map(lines[i])
            var x2 = x1
            var y2 = y1
            when (dir) {
                'L' -> x2 -= amount
                'U' -> y2 -= amount
                'R' -> x2 += amount
                'D' -> y2 += amount
            }
            area += (x1 * y2) - (x2 * y1)
            outline += amount
            x1 = x2
            y1 = y2
        }
        return outline / 2 + 1 + area.absoluteValue / 2
    }
}
