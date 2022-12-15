package advent.year2022

import java.util.concurrent.atomic.AtomicInteger

class Day13 {
    private var previousLeft: String? = null
    private var pairIndex = 1
    private val rightOrderIndexes = mutableListOf<Int>()

    fun add(line: String) {
        if (line.isBlank()) {
            pairIndex++
            previousLeft = null
            return
        }
        val left = previousLeft
        if (left == null) {
            previousLeft = line
        } else {
            if (compareBoolean(left, line)) rightOrderIndexes.add(pairIndex)
        }
    }

    fun compareBoolean(left: String, right: String): Boolean = compare(left, right) <= 0

    fun compare(left: String, right: String): Int = compareLists(parse(left), parse(right))

    private fun compareLists(left: List<*>, right: List<*>): Int {
        val minLength = left.size.coerceAtMost(right.size)
        for (i in 0 until minLength) {
            if (i > left.lastIndex) return -1
            if (i > right.lastIndex) return 1
            val leftElement = left[i]
            val rightElement = right[i]
            if (leftElement is Int && rightElement is Int) {
                val compare = leftElement.compareTo(rightElement)
                if (compare != 0) return compare
            } else if (leftElement is List<*> && rightElement is List<*>) {
                val compare = compareLists(leftElement, rightElement)
                if (compare != 0) return compare
            } else if (leftElement is Int) {
                val compare = compareLists(left.drop(i), rightElement as List<*>)
                if (compare != 0) return compare
            } else if (rightElement is Int) {
                val compare = compareLists(leftElement as List<*>, right.drop(i))
                if (compare != 0) return compare
            }
        }
        return left.size - right.size
    }

    fun sumOfRightOrderIndexes(): Int = rightOrderIndexes.sum()

    private fun parse(line: String): List<*> = line.parseList(AtomicInteger(0))

    private fun String.parseList(index: AtomicInteger): List<*> {
        index.getAndIncrement()
        val list = mutableListOf<Any>()
        while (index.get() < length) {
            val charAt = get(index.get())
            if (charAt.isDigit()) {
                list.add(parseDigit(index))
            } else if (charAt == ']') {
                index.getAndIncrement()
                break
            } else if (charAt == ',') {
                index.getAndIncrement()
            } else if (charAt == '[') {
                list.add(parseList(index))
            }
        }
        return list
    }

    private fun String.parseDigit(index: AtomicInteger): Int {
        val endIndex = indexOfAny(charArrayOf(',', ']'), index.get())
        val number = substring(index.get(), endIndex).toInt()
        index.set(endIndex)
        return number
    }
}
