package advent.year2015

import advent.assert
import advent.readFile
import org.junit.Assert
import org.junit.Test

class Day12Test {
    @Test
    fun samples() {
        val cases = listOf(
            "[1,2,3]" to 6,
            "{\"a\":2,\"b\":4}" to 6,
            "[[[3]]]" to 3,
            "{\"a\":{\"b\":4},\"c\":-1}" to 3,
            "{\"a\":[-1,1]}" to 0,
            "[-1,{\"a\":1}]" to 0,
            "[]" to 0,
            "{}" to 0
        )
        val result = buildString {
            cases.forEach { (input, expect) ->
                val result = Day12.numberSum(input)
                runCatching { result.assert(expect) }
                    .onFailure {
                        appendLine("failed with $input got: $result expected: $expect")
                    }
            }
        }
        if (result.isNotBlank()) Assert.fail(result)
    }

    @Test
    fun `samples part 2`() {
        val cases = listOf(
            "[1,2,3]" to 6,
            "{\"a\":2,\"b\":4}" to 6,
            "[[[3]]]" to 3,
            "{\"a\":{\"b\":4},\"c\":-1}" to 3,
            "{\"a\":[-1,1]}" to 0,
            "[-1,{\"a\":1}]" to 0,
            "[]" to 0,
            "{}" to 0,
            "[1,{\"c\":\"red\",\"b\":2},3]" to 4,
            "{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}" to 0,
            "[1,\"red\",5]" to 6
        )
        val result = buildString {
            cases.forEach { (input, expect) ->
                val result = Day12.numberSumWithoutRed(input)
                runCatching { result.assert(expect) }
                    .onFailure {
                        appendLine("failed with $input got: $result expected: $expect")
                    }
            }
        }
        if (result.isNotBlank()) Assert.fail(result)
    }

    @Test
    fun actual() {
        val input = readFile("2015/Day12")
        Day12.numberSum(input).assert(156366)
        Day12.numberSumWithoutRed(input).assert(96852)
    }
}
