package advent.year2022

import kotlin.math.absoluteValue

object Day18 {
    fun calculateTotalArea(input: String): Long {
        val lines = input.split("\n")
        val count = lines.size
        val pointXs = IntArray(count)
        val pointYs = IntArray(count)
        val pointZs = IntArray(count)
        fillArrays(lines, pointXs, pointYs, pointZs)
        var sides = 0L
        for (i in 0 until count) {
            val pointX = pointXs[i]
            val pointY = pointYs[i]
            val pointZ = pointZs[i]
            var pointSides = 6
            for (j in 0 until count) {
                if (i == j) continue
                val xDif = pointX - pointXs[j]
                val yDif = pointY - pointYs[j]
                val zDif = pointZ - pointZs[j]
                if (xDif.absoluteValue + yDif.absoluteValue + zDif.absoluteValue == 1) pointSides--
            }
            sides += pointSides
        }
        return sides
    }

    private fun fillArrays(
        lines: List<String>,
        pointXs: IntArray,
        pointYs: IntArray,
        pointZs: IntArray
    ) {
        for (i in lines.indices) {
            val line = lines[i]
            val firstComma = line.indexOf(',')
            pointXs[i] = line.substring(0, firstComma).toInt()
            val secondComma = line.indexOf(',', firstComma + 1)
            pointYs[i] = line.substring(firstComma + 1, secondComma).toInt()
            pointZs[i] = line.substring(secondComma + 1, line.length).toInt()
        }
    }

    fun calculateOutsideArea(input: String): Long {
        val lines = input.split("\n")
        val count = lines.size
        val pointXs = IntArray(count)
        val pointYs = IntArray(count)
        val pointZs = IntArray(count)
        fillArrays(lines, pointXs, pointYs, pointZs)
        var sides = 0L
        val minDistanceBuffer = IntArray(6) { -1 }
        for (i in 0 until count) {
            val pointX = pointXs[i]
            val pointY = pointYs[i]
            val pointZ = pointZs[i]
            for (j in 0 until count) {
                if (i == j) continue
                val xDif = pointX - pointXs[j]
                val yDif = pointY - pointYs[j]
                val zDif = pointZ - pointZs[j]
                if (yDif == 0 && zDif == 0) {
                    minDistanceBuffer.updateMinDistanceAt(0, xDif)
                } else if (xDif == 0 && zDif == 0) {
                    minDistanceBuffer.updateMinDistanceAt(1, yDif)
                } else if (xDif == 0 && yDif == 0) {
                    minDistanceBuffer.updateMinDistanceAt(2, zDif)
                }
            }
            val countSides = minDistanceBuffer.freeSides()
            sides += countSides
            println("$pointX, $pointY, $pointZ ${minDistanceBuffer.contentToString()} $countSides")
            for (b in minDistanceBuffer.indices) {
                minDistanceBuffer[b] = -1
            }
        }
        return sides
    }

    private fun IntArray.freeSides(): Int {
        var freeSides = 0
        if (this[0] == -1) {
            freeSides++
        } else if (this[0] > 1) {
            if (this[2] == -1 && this[3] == -1 || this[4] == -1 && this[5] == -1) {
                freeSides++
            }
        }
        if (this[1] == -1) {
            freeSides++
        } else if (this[1] > 1) {
            if (this[2] == -1 && this[3] == -1 || this[4] == -1 && this[5] == -1) {
                freeSides++
            }
        }
        if (this[2] == -1) {
            freeSides++
        } else if (this[2] > 1) {
            if (this[0] == -1 && this[1] == -1 || this[4] == -1 && this[5] == -1) {
                freeSides++
            }
        }
        if (this[3] == -1) {
            freeSides++
        } else if (this[3] > 1) {
            if (this[0] == -1 && this[1] == -1 || this[4] == -1 && this[5] == -1) {
                freeSides++
            }
        }
        if (this[4] == -1) {
            freeSides++
        } else if (this[4] > 1) {
            if (this[0] == -1 && this[1] == -1 || this[2] == -1 && this[3] == -1) {
                freeSides++
            }
        }
        if (this[5] == -1) {
            freeSides++
        } else if (this[5] > 1) {
            if (this[0] == -1 && this[1] == -1 || this[2] == -1 && this[3] == -1) {
                freeSides++
            }
        }
        return freeSides
    }

    private fun IntArray.updateMinDistanceAt(index: Int, candidate: Int) {
        if (candidate < 0) {
            updateMinAt(index * 2, -candidate)
        } else if (candidate > 0) {
            updateMinAt(index * 2 + 1, candidate)
        }
    }

    private fun IntArray.updateMinAt(index: Int, candidate: Int) {
        val current = this[index]
        if (current == -1 || current > candidate) this[index] = candidate
    }
}
