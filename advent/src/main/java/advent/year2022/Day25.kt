package advent.year2022

object Day25 {
    fun toSnafu(input: Long): String {
        if (input < 3) {
            return input.toString()
        }
        var leftover = input
        return buildString {
            while (leftover > 0) {
                val value = leftover % 5
                value.toSnafu { char, addToNext ->
                    append(char)
                    leftover = leftover / 5 + addToNext
                }
            }
            reverse()
        }
    }

    private inline fun Long.toSnafu(onSnafuAndNext: (Char, Long) -> Unit) {
        when (this) {
            0L -> onSnafuAndNext('0', 0L)
            1L -> onSnafuAndNext('1', 0L)
            2L -> onSnafuAndNext('2', 0L)
            3L -> onSnafuAndNext('=', 1L)
            4L -> onSnafuAndNext('-', 1L)
            else -> println("on no $this")
        }
    }

    fun fromSnafu(input: String): Long {
        var multiply = 1L
        var sum = 0L
        for (i in input.lastIndex downTo 0) {
            sum += multiply * input[i].snafuValue
            multiply *= 5
        }
        return sum
    }

    private val Char.snafuValue: Long
        get() = when (this) {
            '2' -> 2
            '1' -> 1
            '0' -> 0
            '-' -> -1
            '=' -> -2
            else -> throw IllegalArgumentException("unexpected char ($this)")
        }
}
