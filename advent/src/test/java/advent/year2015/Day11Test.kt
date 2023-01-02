package advent.year2015

import advent.assert
import advent.assertFalse
import advent.assertTrue
import org.junit.Test

class Day11Test {
    @Test
    fun `isValid sample 1`() {
        Day11.isValid("hijklmmn").assertFalse()
    }

    @Test
    fun `isValid sample 2`() {
        Day11.isValid("abbceffg").assertFalse()
    }

    @Test
    fun `isValid sample 3`() {
        Day11.isValid("abbcegjk").assertFalse()
    }

    @Test
    fun `isValid sample 4`() {
        Day11.isValid("abcdffaa").assertTrue()
    }

    @Test
    fun `isValid sample 5`() {
        Day11.isValid("ghjaabcc").assertTrue()
    }

    @Test
    fun `next sample 1`() {
        Day11.next("abcdefgh").assert("abcdffaa")
    }

    @Test
    fun `next sample 2`() {
        Day11.next("ghijklmn").assert("ghjaabcc")
    }

    @Test
    fun actual() {
        val next = Day11.next("hepxcrrq")
        next.assert("hepxxyzz")
        Day11.next(next).assert("heqaabcc")
    }
}
