package advent.year2022

import advent.packInts
import advent.unpackInt1
import kotlin.math.absoluteValue
import kotlin.math.sign

class Day20(val values: IntArray) {
    fun getAfter(afterValue: Int, indexAfter: Int): Int = Companion.getAfter(values, afterValue, indexAfter)

    fun perMutate(times: Int = values.size): IntArray {
        val mutationArray = LongArray(values.size) { index -> packInts(values[index], index) }
        val valueArray = mutationArray.copyOf()
        repeat(times) { mutation ->
            val element = valueArray[mutation]
            val elementIndex = mutationArray.indexOf(element)
            val swapCount = unpackInt1(element) % values.lastIndex
            val sign = swapCount.sign
            var index = elementIndex
            repeat(swapCount.absoluteValue) {
                index = swap(mutationArray, index, sign)
            }
        }
        return IntArray(values.size) { unpackInt1(mutationArray[it]) }
    }

    fun perMutateWithKey(key: Long): LongArray {
        val mutationArray = Array(values.size) { index -> ValueWithIndex(values[index] * key, index) }
        val valueArray = mutationArray.copyOf()
        doubleRepeat(10, values.size) { mutation ->
            val element = valueArray[mutation]
            val elementIndex = mutationArray.indexOf(element)
            val swapCount = element.value % values.lastIndex
            val sign = swapCount.sign
            var index = elementIndex
            repeat(swapCount.absoluteValue.toInt()) {
                index = swapAny(mutationArray, index, sign)
            }
        }

        return LongArray(values.size) { index -> mutationArray[index].value }
    }

    private inline fun doubleRepeat(repeatOut: Int, repeatInner: Int, action: (Int) -> Unit) {
        repeat(repeatOut) { repeat(repeatInner, action) }
    }

    private fun swap(array: LongArray, from: Int, direction: Int): Int {
        val nextIndex = from + direction
        val safeIndex = when {
            nextIndex < 0 -> array.lastIndex
            nextIndex > array.lastIndex -> 0
            else -> nextIndex
        }
        array[from].let {
            array[from] = array[safeIndex]
            array[safeIndex] = it
        }
        return safeIndex
    }

    private fun <T : Any> swapAny(array: Array<T>, from: Int, direction: Int): Int {
        val nextIndex = from + direction
        val safeIndex = when {
            nextIndex < 0 -> array.lastIndex
            nextIndex > array.lastIndex -> 0
            else -> nextIndex
        }
        array[from].let {
            array[from] = array[safeIndex]
            array[safeIndex] = it
        }
        return safeIndex
    }

    companion object {
        fun fromInput(input: String): Day20 {
            val values = input.split("\n").map { it.toInt() }.toIntArray()
            return Day20(values)
        }

        fun getAfter(array: IntArray, afterValue: Int, indexesAfter: Int): Int {
            val index = array.indexOf(afterValue) + indexesAfter
            return array[index % array.size]
        }

        fun getAfter(array: LongArray, afterValue: Long, indexesAfter: Int): Long {
            val index = array.indexOf(afterValue) + indexesAfter
            return array[index % array.size]
        }
    }

    private data class ValueWithIndex(val value: Long, val index: Int)
}
