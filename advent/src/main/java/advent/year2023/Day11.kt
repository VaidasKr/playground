package advent.year2023

import kotlin.math.absoluteValue

object Day11 {
    fun shortestPathSumAfterExpanding(input: String, expand: Int): Long {
        val map = input.split('\n').filter { it.isNotBlank() }
        val expandPlanets = planetsAfterExpanding(map, expand)
        return sumOfShortestPaths(expandPlanets)
    }

    private fun planetsAfterExpanding(map: List<String>, expand: Int): List<Pair<Int, Int>> {
        val planets = mutableListOf<Pair<Int, Int>>()
        val rowsHasPlanet = BooleanArray(map.size)
        val columnHasPlanet = BooleanArray(map.first().length)
        for (y in map.indices) {
            val line = map[y]
            for (x in line.indices) {
                if (line[x] != '.') {
                    columnHasPlanet[x] = true
                    rowsHasPlanet[y] = true
                    planets.add(x to y)
                }
            }
        }
        return planets.map { (x, y) ->
            var mappedY = y
            var mappedX = x
            for (row in 0 until y) {
                if (!rowsHasPlanet[row]) {
                    mappedY += expand - 1
                }
            }
            for (col in 0 until x) {
                if (!columnHasPlanet[col]) {
                    mappedX += expand - 1
                }
            }
            mappedX to mappedY
        }
    }

    private fun sumOfShortestPaths(planets: List<Pair<Int, Int>>): Long {
        var sum = 0L
        for (i in 0 until planets.size - 1) {
            val first = planets[i]
            for (j in i + 1 until planets.size) {
                val second = planets[j]
                sum += (first.first - second.first).absoluteValue + (first.second - second.second).absoluteValue
            }
        }
        return sum
    }
}
