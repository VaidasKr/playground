package advent.year2015

class Day24 {
    private val packages = Packages()

    fun addPackage(weight: Int) {
        packages.add(weight)
    }

    fun bestBucketCombination(bucketCount: Int): Long {
        val partWeight = packages.sum / bucketCount

        var possibleBuckets = emptySet<Bucket>()

        var size = 1

        val availablePackages = packages.array
        availablePackages.sortDescending()

        while (possibleBuckets.isEmpty()) {
            possibleBuckets = createBuckets(partWeight, size++, availablePackages, bucketCount)
        }

        return possibleBuckets.minOf { it.qe }
    }

    private fun createBuckets(
        bucketWeight: Int,
        bucketSize: Int,
        availablePackages: IntArray,
        bucketCount: Int
    ): Set<Bucket> {
        var maxForSize = 0
        for (i in 0 until bucketSize) {
            maxForSize += availablePackages[i]
        }
        if (maxForSize < bucketWeight) return emptySet()

        val hashSet = hashSetOf<Bucket>()
        val checkBuffer = IntArray(availablePackages.size - bucketSize)
        val skipBuffer = IntArray(bucketSize)

        val indices = availablePackages.indices
        bucketLoop(IntArray(bucketSize), 0, 0, bucketWeight, 0, availablePackages) { bucket ->
            bucket.elements.copyInto(skipBuffer)
            var addIndex = 0
            for (i in indices) {
                val element = availablePackages[i]
                val indexInSkip = skipBuffer.indexOf(element)
                if (indexInSkip == -1) {
                    checkBuffer[addIndex++] = element
                } else {
                    skipBuffer[indexInSkip] = -1
                }
            }
            if (hasAvailableBuckets(checkBuffer, bucketWeight, bucketCount - 1)) {
                hashSet.add(bucket)
            }
        }

        return hashSet
    }

    private fun bucketLoop(
        destination: IntArray,
        atIndex: Int,
        index: Int,
        neededWeight: Int,
        sum: Int,
        array: IntArray,
        onBucket: (Bucket) -> Unit
    ) {
        if (sum > neededWeight) return
        if (atIndex == destination.size) {
            if (sum == neededWeight) {
                onBucket(Bucket(destination.copyOf()))
            }
            return
        }
        for (i in index until array.size) {
            destination[atIndex] = array[i]
            val newSum = sum + array[i]
            bucketLoop(destination, atIndex + 1, i + 1, neededWeight, newSum, array, onBucket)
        }
    }

    private fun hasAvailableBuckets(checkBuffer: IntArray, bucketWeight: Int, bucketCount: Int): Boolean {
        var minBucketSize = 0
        var sum = 0
        while (sum < bucketWeight && minBucketSize < checkBuffer.size) {
            sum += checkBuffer[minBucketSize++]
        }
        if (sum < bucketWeight) return false

        if (bucketCount == 2) {
            return checkForBuckets(minBucketSize, bucketWeight, checkBuffer)
        } else {
            var size = minBucketSize
            var possibleBuckets = emptySet<Bucket>()
            while (possibleBuckets.isEmpty() && size < checkBuffer.size) {
                possibleBuckets = createBuckets(bucketWeight, size++, checkBuffer, bucketCount)
            }
            return possibleBuckets.isNotEmpty()
        }
    }

    private fun checkForBuckets(minSize: Int, bucketWeight: Int, checkBuffer: IntArray): Boolean {
        for (i in minSize until checkBuffer.size) {
            val destination = IntArray(i)
            if (hasBucketLoop(destination, 0, 0, bucketWeight, 0, checkBuffer)) {
                return true
            }
        }
        return false
    }

    private fun hasBucketLoop(
        destination: IntArray,
        atIndex: Int,
        index: Int,
        neededWeight: Int,
        sum: Int,
        array: IntArray
    ): Boolean {
        if (sum > neededWeight) return false
        if (atIndex == destination.size) {
            return sum == neededWeight
        }
        for (i in index until array.size) {
            destination[atIndex] = array[i]
            val newSum = sum + array[i]
            if (hasBucketLoop(destination, atIndex + 1, i + 1, neededWeight, newSum, array)) {
                return true
            }
        }
        return false
    }

    class Bucket(val elements: IntArray) {
        val qe: Long get() = elements.fold(1L) { acc: Long, i: Int -> acc * i }

        override fun hashCode(): Int = elements.contentHashCode()

        override fun equals(other: Any?): Boolean =
            other != null && other is Bucket && elements.contentEquals(other.elements)

        override fun toString(): String {
            return "Bucket(${elements.contentToString()})"
        }
    }

    class Packages {
        var inner = IntArray(10)
        var size = 0

        val sum: Int
            get() {
                var sum = 0
                for (i in 0 until size) sum += inner[i]
                return sum
            }

        val array get() = inner.copyOf(size)

        fun add(value: Int) {
            val arraySize = inner.size
            if (size >= arraySize) {
                val newArray = IntArray(arraySize + 10) { index ->
                    if (index < arraySize) {
                        inner[index]
                    } else {
                        0
                    }
                }
                inner = newArray
            }
            inner[size++] = value
        }
    }
}
