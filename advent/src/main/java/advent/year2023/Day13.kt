package advent.year2023

import kotlin.math.min

object Day13 {
    fun findSummarizedMirrorSum(input: String, dif: Int): Long =
        input.trim().split("\n\n")
            .sumOf { blockString ->
                val blockLines = blockString.split("\n")
                val length = blockLines.first().length
                val vertical = findVerticalIndex(blockLines, length, dif)
                val horizontal = findHorizontalIndex(blockLines, length, dif) * 100
                (vertical + horizontal).toLong()
            }

    private fun findVerticalIndex(block: List<String>, length: Int, dif: Int): Int {
        for (i in 0 until length - 1) {
            if (matchVertically(block, i, length, dif)) return i + 1
        }
        return 0
    }

    private fun matchVertically(block: List<String>, index: Int, length: Int, dif: Int): Boolean {
        var allowedDif = dif
        for (i in 0 until min(index + 1, length - index - 1)) {
            for (ci in block.indices) {
                if (block[ci][index - i] != block[ci][index + i + 1] && allowedDif-- == 0) return false
            }
        }
        return allowedDif == 0
    }

    private fun findHorizontalIndex(block: List<String>, length: Int, dif: Int): Int {
        for (i in 0 until block.size - 1) {
            if (matchHorizontally(block, i, length, dif)) return (i + 1)
        }
        return 0
    }

    private fun matchHorizontally(block: List<String>, index: Int, length: Int, dif: Int): Boolean {
        var allowedDif = dif
        for (i in 0 until min(index + 1, block.size - index - 1)) {
            for (ci in 0 until length) {
                if (block[index - i][ci] != block[index + i + 1][ci] && allowedDif-- == 0) return false
            }
        }
        return allowedDif == 0
    }
}
