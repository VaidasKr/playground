package advent

import kotlin.math.absoluteValue

object Day15 {
    fun noBeaconAtLine(input: String, lineNumber: Int): Int {
        val knownBeaconAtLine = hashSetOf<Int>()
        val noPossibleBeaconsOnLine = hashSetOf<Int>()
        input.trim().split("\n").forEach { line ->
            var xIndex = line.indexOf('x')
            var yIndex = line.indexOf('y')
            val separator = line.indexOf(':')
            val sensorX = line.substring(xIndex + 2, yIndex - 2).toInt()
            val sensorY = line.substring(yIndex + 2, separator).toInt()
            xIndex = line.indexOf('x', separator)
            yIndex = line.indexOf('y', separator)
            val beaconX = line.substring(xIndex + 2, yIndex - 2).toInt()
            val beaconY = line.substring(yIndex + 2, line.length).toInt()
            if (beaconY == lineNumber) knownBeaconAtLine.add(beaconX)
            val distance = (sensorX - beaconX).absoluteValue + (sensorY - beaconY).absoluteValue
            val distanceToLine = (sensorY - lineNumber).absoluteValue
            val dif = distance - distanceToLine
            if (dif > 0) {
                val from = sensorX - dif
                val to = sensorX + dif
                for (x in from..to) {
                    noPossibleBeaconsOnLine.add(x)
                }
            }
            println()
        }
        return noPossibleBeaconsOnLine.filter { !knownBeaconAtLine.contains(it) }.size
    }

    fun possibleBeaconFreq2(input: String, size: Int): Long {
        val split = input.trim().split("\n")
        val sensorCount = split.size
        val sensorX = IntArray(sensorCount)
        val sensorY = IntArray(sensorCount)
        val sensorDistance = IntArray(sensorCount)
        split.forEachIndexed { index, line ->
            var xIndex = line.indexOf('x')
            var yIndex = line.indexOf('y')
            val separator = line.indexOf(':')
            sensorX[index] = line.substring(xIndex + 2, yIndex - 2).toInt()
            sensorY[index] = line.substring(yIndex + 2, separator).toInt()
            xIndex = line.indexOf('x', separator)
            yIndex = line.indexOf('y', separator)
            val beaconX = line.substring(xIndex + 2, yIndex - 2).toInt()
            val beaconY = line.substring(yIndex + 2, line.length).toInt()
            sensorDistance[index] = (sensorX[index] - beaconX).absoluteValue + (sensorY[index] - beaconY).absoluteValue
        }
        return frequency(size, sensorX, sensorY, sensorDistance)
    }

    private fun frequency(size: Int, sensorX: IntArray, sensorY: IntArray, sensorDistance: IntArray): Long {
        var y = 0
        var x: Int
        var nextX: Int
        var iterations = 0
        while (y <= size) {
            x = 0
            while (x <= size) {
                iterations++
                nextX = nextXPosForLine(sensorX, sensorY, sensorDistance, x, y)
                if (nextX == -1) {
                    println("iterations: $iterations x: $x y: $y")
                    return x * 4_000_000L + y
                }
                x = nextX
            }
            y++
        }
        return 0
    }

    private fun nextXPosForLine(
        sensorsX: IntArray,
        sensorsY: IntArray,
        sensorsDistance: IntArray,
        x: Int,
        y: Int
    ): Int {
        for (i in sensorsX.indices) {
            val distance = (sensorsX[i] - x).absoluteValue + (sensorsY[i] - y).absoluteValue
            if (distance <= sensorsDistance[i]) {
                val distanceToLine = (sensorsY[i] - y).absoluteValue
                val dif = sensorsDistance[i] - distanceToLine
                return sensorsX[i] + dif + 1
            }
        }
        return -1
    }

    private fun inSensorBounds(
        sensorX: IntArray,
        sensorY: IntArray,
        sensorDistance: IntArray,
        x: Int,
        y: Int,
    ): Boolean {
        for (i in sensorX.indices) {
            val distance = (sensorX[i] - x).absoluteValue + (sensorY[i] - y).absoluteValue
            if (distance <= sensorDistance[i]) return true
        }
        return false
    }
}
