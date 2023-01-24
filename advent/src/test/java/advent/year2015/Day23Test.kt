package advent.year2015

import advent.assert
import advent.readFile
import org.junit.Test

class Day23Test {
    @Test
    fun sample() {
        val input = """inc a
jio a, +2
tpl a
inc a"""
        Day23.calculateRegisters(input)["a"]!!.assert(2)
    }

    @Test
    fun actual() {
        val input = readFile("2015/Day23")
        Day23.calculateRegisters(input).getOrElse("b") { 0 }.assert(255)
        Day23.calculateRegisters(input, mapOf("a" to 1)).getOrElse("b") { 0 }.assert(334)
    }
}
