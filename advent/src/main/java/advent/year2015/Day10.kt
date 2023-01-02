package advent.year2015

object Day10 {
    fun transform(input: String): String = buildString {
        var num = input.first().digitToInt()
        var count = 1
        for (i in 1 until input.length) {
            val digit = input[i].digitToInt()
            if (digit != num) {
                append(count)
                append(num)
                count = 1
                num = digit
            } else {
                count++
            }
        }
        append(count)
        append(num)
    }
}
