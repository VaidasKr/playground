package advent.year2015

import java.security.MessageDigest

object Day4 {
    fun findZeroLeadHash(input: String): Int = findZeroLeadHash(input) { startsWithFiveZeros() }

    fun findZeroLeadHash2(input: String): Int = findZeroLeadHash(input) { startsWithSixZeros() }

    private inline fun findZeroLeadHash(input: String, predicate: ByteArray.() -> Boolean): Int {
        val digest = MessageDigest.getInstance("MD5")
        var number = 1
        var test: String
        var generated: ByteArray
        while (true) {
            test = "$input${number++}"
            generated = digest.digest(test.toByteArray())
            if (predicate(generated)) return number - 1
        }
    }

    private fun ByteArray.startsWithFiveZeros(): Boolean =
        get(0).toUByte().toInt() == 0 && get(1).toUByte().toInt() == 0 && get(2).toUByte().toInt() <= 0xf

    private fun ByteArray.startsWithSixZeros(): Boolean =
        get(0).toUByte().toInt() == 0 && get(1).toUByte().toInt() == 0 && get(2).toUByte().toInt() == 0
}

