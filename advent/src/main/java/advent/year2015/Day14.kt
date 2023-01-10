package advent.year2015

import java.lang.Integer.min

class Day14(input: String) {
    private val names: List<String>
    private val speed: List<Int>
    private val endurance: List<Int>
    private val rest: List<Int>

    init {
        val names = mutableListOf<String>()
        val speed = mutableListOf<Int>()
        val endurance = mutableListOf<Int>()
        val rest = mutableListOf<Int>()
        input.lineSequence().forEach { line ->
            val spaceIndex = line.indexOf(' ')
            names.add(line.substring(0, spaceIndex))
            var number = 0
            var target = speed
            for (i in line.indices) {
                val char = line[i]
                if (char.isDigit()) {
                    number = number * 10 + char.digitToInt()
                } else if (number != 0) {
                    target.add(number)
                    when (target) {
                        speed -> target = endurance
                        endurance -> target = rest
                        rest -> target = speed
                    }
                    number = 0
                }
            }
        }
        this.names = names
        this.speed = speed
        this.endurance = endurance
        this.rest = rest
    }

    fun maxDistanceAfter(time: Int): Int {
        var max = 0
        for (i in names.indices) {
            val distance = maxDistanceFor(time, speed[i], endurance[i], rest[i])
            if (distance > max) {
                max = distance
            }
        }
        return max
    }

    private fun maxDistanceFor(time: Int, speed: Int, speedFor: Int, rest: Int): Int {
        val iterationTime = speedFor + rest
        val fullIterations = time / iterationTime
        val movingLeft = min(time - fullIterations * iterationTime, speedFor)
        return (fullIterations * speedFor + movingLeft) * speed
    }

    fun maxScoreAfter(time: Int): Int {
        val size = names.size
        val distances = IntArray(size)
        val score = IntArray(size)
        for (second in 0 until time) {
            for (i in 0 until size) {
                val speed = speed[i]
                val endurance = endurance[i]
                val rest = rest[i]
                val advance: Int = if (second % (endurance + rest) < endurance) speed else 0
                distances[i] = distances[i] + advance
            }
            var max = 0
            for (i in 0 until size) {
                val distance = distances[i]
                if (distance > max) max = distance
            }
            for (i in 0 until size) {
                if (distances[i] == max) {
                    score[i] = score[i] + 1
                }
            }
        }
        return score.max()
    }
}
