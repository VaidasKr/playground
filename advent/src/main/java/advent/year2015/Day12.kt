package advent.year2015

import java.util.concurrent.atomic.AtomicInteger

object Day12 {
    fun numberSum(input: String): Int {
        var sum = 0
        var hasNegative = false
        var number = 0
        var hasNumber = false
        input.forEach { char ->
            if (char.isDigit()) {
                if (hasNumber) {
                    number = number * 10 + char.digitToInt()
                } else {
                    hasNumber = true
                    number = char.digitToInt()
                }
            } else if (char == '-' && !hasNegative) {
                hasNegative = true
            } else {
                if (hasNumber) {
                    if (hasNegative) {
                        number *= -1
                    }
                    sum += number
                }
                hasNegative = false
                hasNumber = false
            }
        }
        return sum
    }

    fun numberSumWithoutRed(input: String): Int {
        val iterator = input.iterator()
        return when (iterator.next()) {
            '[' -> iterator.readArray()
            '{' -> iterator.readObject()
            else -> 0
        }
    }

    private fun CharIterator.readObject(): Int {
        var sum = 0
        var isValue = false
        var isRed = false
        while (hasNext()) {
            val next = next()
            if (next == '}') return if (isRed) 0 else sum
            if (isValue) {
                when (next) {
                    '{' -> sum += readObject()
                    '[' -> sum += readArray()
                    '"' -> {
                        val readString = readString()
                        if (readString == "red") isRed = true
                    }
                    else -> {
                        var char = next
                        val numberRef = AtomicInteger()
                        if (next == '-' || next.isDigit()) {
                            char = readNumber(next, numberRef)
                        }
                        sum += numberRef.get()
                        if (char == '}') return if (isRed) 0 else sum
                    }
                }
                isValue = false
            } else {
                when (next) {
                    '"' -> readString()
                    ':' -> isValue = true
                }
            }
        }
        return if (isRed) 0 else sum
    }

    private fun CharIterator.readNumber(char: Char, numberRef: AtomicInteger): Char {
        var isNegative = false
        if (char.isDigit()) {
            numberRef.set(char.digitToInt())
        } else {
            isNegative = true
        }
        while (hasNext()) {
            val next = next()
            if (next.isDigit()) {
                numberRef.updateAndGet { it * 10 + next.digitToInt() }
            } else {
                if (isNegative) {
                    numberRef.updateAndGet { it * -1 }
                }
                return next
            }
        }
        throw IllegalArgumentException("I lost somewhere")
    }

    private fun CharIterator.readString(): String {
        return buildString {
            while (hasNext()) {
                val next = next()
                if (next == '"') {
                    break
                } else {
                    append(next)
                }
            }
        }
    }

    private fun CharIterator.readArray(): Int {
        var sum = 0
        while (hasNext()) {
            when (val next = next()) {
                ']' -> return sum
                '[' -> sum += readArray()
                '{' -> sum += readObject()
                '"' -> readString()
                else -> {
                    if (next.isDigit() || next == '-') {
                        var char = next
                        val numberRef = AtomicInteger()
                        if (next == '-' || next.isDigit()) {
                            char = readNumber(next, numberRef)
                        }
                        sum += numberRef.get()
                        if (char == ']') return sum
                    }
                }
            }
        }
        return sum
    }
}
