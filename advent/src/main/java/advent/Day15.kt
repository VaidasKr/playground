package advent

import kotlin.math.absoluteValue

class Day15(input: String) {
    private val sensorCount: Int
    private val sensors: IntArray
    private val sensorDistance: IntArray
    private val beacons: IntArray

    init {
        val split = input.trim().split("\n")
        sensorCount = split.size
        sensors = IntArray(sensorCount * 2)
        beacons = IntArray(sensorCount * 2)
        sensorDistance = IntArray(sensorCount)
        split.forEachIndexed { index, line ->
            var xIndex = line.indexOf('x')
            var yIndex = line.indexOf('y')
            val separator = line.indexOf(':')
            sensors[2 * index] = line.substring(xIndex + 2, yIndex - 2).toInt()
            sensors[2 * index + 1] = line.substring(yIndex + 2, separator).toInt()
            xIndex = line.indexOf('x', separator)
            yIndex = line.indexOf('y', separator)
            beacons[2 * index] = line.substring(xIndex + 2, yIndex - 2).toInt()
            beacons[2 * index + 1] = line.substring(yIndex + 2, line.length).toInt()
            sensorDistance[index] =
                (sensors[2 * index] - beacons[2 * index]).absoluteValue +
                    (sensors[2 * index + 1] - beacons[2 * index + 1]).absoluteValue
        }
    }

    fun noPossibleBeaconCountOn(lineNumber: Int): Int {
        val usedPoints = hashSetOf<Int>()
        for (i in 0 until sensorCount) {
            if (beacons[i * 2 + 1] == lineNumber) {
                usedPoints.add(beacons[i * 2])
            }
        }
        var count = 0
        for (i in 0 until sensorCount) {
            val sensorX = sensors[i * 2]
            val sensorY = sensors[i * 2 + 1]
            val distanceToLine = (sensorY - lineNumber).absoluteValue
            val dif = sensorDistance[i] - distanceToLine
            if (dif > 0) {
                val from = sensorX - dif
                val to = sensorX + dif
                for (x in from..to) {
                    if (usedPoints.add(x)) count++
                }
            }
        }
        return count
    }

    fun possibleBeaconFrequency(size: Int): Long {
        var x: Int
        var lastX: Int
        var iterations = 0
        for (y in 0..size) {
            x = 0
            while (x <= size) {
                iterations++
                lastX = lastXforNearestSensor(x, y)
                if (lastX == -1) {
                    println("iterations: $iterations x: $x y: $y")
                    return x * 4_000_000L + y
                }
                x = lastX + 1
            }
        }
        return 0
    }

    private fun lastXforNearestSensor(x: Int, y: Int): Int {
        for (i in 0 until sensorCount) {
            val sensorX = sensors[i * 2]
            val sensorY = sensors[i * 2 + 1]
            val distance = (sensorX - x).absoluteValue + (sensorY - y).absoluteValue
            if (distance <= sensorDistance[i]) {
                val distanceToLine = (sensorY - y).absoluteValue
                val dif = sensorDistance[i] - distanceToLine
                return sensorX + dif
            }
        }
        return -1
    }
}
