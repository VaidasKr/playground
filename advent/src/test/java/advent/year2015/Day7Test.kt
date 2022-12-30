package advent.year2015

import advent.print
import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day7Test {
    @Test
    fun sample() {
        val input = """
            123 -> x
            456 -> y
            x AND y -> d
            x OR y -> e
            x LSHIFT 2 -> f
            y RSHIFT 2 -> g
            NOT x -> h
            NOT y -> i
        """.trimIndent()

        val actual = Day7.toValueMap(input)

        val expected =
            mapOf(
                "d" to 72u,
                "e" to 507u,
                "f" to 492u,
                "g" to 114u,
                "h" to 65412u,
                "i" to 65079u,
                "x" to 123u,
                "y" to 456u
            )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun actual() {
        val toValueMap = Day7.toValueMap(readFile("2015/Day7"))
        toValueMap.forEach {
            if (it.value > 65535u) {
                println(it.key)
            }
        }
        println(toValueMap)
        toValueMap["a"]!!.toInt().print()
    }

    private fun BooleanArray.fill(): BooleanArray = copyInto(BooleanArray(16))
}
