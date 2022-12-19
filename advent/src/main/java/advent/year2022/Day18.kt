package advent.year2022

import kotlin.math.absoluteValue

object Day18 {
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

    fun calculateTotalWithCubes(input: String): Long {
        val toCubes = toCubes(input.split("\n"))
        return calculateFreeSides(toCubes)
    }

    private fun calculateFreeSides(toCubes: List<Cube>): Long {
        var sides = 0L
        for (i in toCubes.indices) {
            val cube = toCubes[i]
            var pointSides = 6
            for (j in toCubes.indices) {
                if (i == j) continue
                if (cube.isTouching(toCubes[j])) pointSides--
            }
            sides += pointSides
        }
        return sides
    }

    private fun toCubes(lines: List<String>): List<Cube> = lines.map { line ->
        val firstComma = line.indexOf(',')
        val x = line.substring(0, firstComma).toInt()
        val secondComma = line.indexOf(',', firstComma + 1)
        val y = line.substring(firstComma + 1, secondComma).toInt()
        val z = line.substring(secondComma + 1, line.length).toInt()
        Cube(x, y, z)
    }

    fun print(input: String) {
        val lines = input.split("\n")
        val count = lines.size
        val pointXs = IntArray(count)
        val pointYs = IntArray(count)
        val pointZs = IntArray(count)
        fillArrays(lines, pointXs, pointYs, pointZs)
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        var minZ = Int.MAX_VALUE
        var maxZ = Int.MIN_VALUE
        for (i in 0 until count) {
            minX = minX.coerceAtMost(pointXs[i])
            maxX = maxX.coerceAtLeast(pointXs[i])
            minY = minY.coerceAtMost(pointYs[i])
            maxY = maxY.coerceAtLeast(pointYs[i])
            minZ = minZ.coerceAtMost(pointZs[i])
            maxZ = maxZ.coerceAtLeast(pointZs[i])
        }
        val zCount = maxZ - minZ + 1
        val yCount = maxY - minY + 1
        val xCount = maxX - minX + 1
        val matrix: Array<Array<IntArray>> = Array(zCount) {
            Array(yCount) {
                IntArray(xCount) { -1 }
            }
        }

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
            matrix[pointZs[i] - minZ][pointYs[i] - minY][pointXs[i] - minX] = pointSides
        }
        val legend = buildString {
            append("  ")
            for (x in 0 until xCount) {
                append(x % 10)
            }
        }
        for (z in 0 until zCount) {
            val square = matrix[z]
            println("z=${z}")
            println(legend)
            for (y in 0 until yCount) {
                print(y % 10)
                print('|')
                val line = square[y]
                for (x in 0 until xCount) {
                    if (line[x] > -1) {
                        print(line[x])
                    } else {
                        print(' ')
                    }
                }
                println('|')
            }
            println(legend)
            println("sides $sides")
        }
    }

    fun calculateOutside(input: String): Long {
        val cubes = toCubes(input.split("\n"))
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE
        var minZ = Int.MAX_VALUE
        var maxZ = Int.MIN_VALUE
        cubes.forEach { cube ->
            minX = minX.coerceAtMost(cube.x)
            maxX = maxX.coerceAtLeast(cube.x)
            minY = minY.coerceAtMost(cube.y)
            maxY = maxY.coerceAtLeast(cube.y)
            minZ = minZ.coerceAtMost(cube.z)
            maxZ = maxZ.coerceAtLeast(cube.z)
        }
        val minEdge = Cube(minX, minY, minZ)
        val maxEdge = Cube(maxX, maxY, maxZ)
        return calculateOutside(cubes, minEdge, maxEdge)
    }

    private fun calculateOutside(lavaCubes: List<Cube>, minEdge: Cube, maxEdge: Cube): Long {
        val lava = hashSetOf<Cube>()
        val air = hashSetOf<Cube>()
        lava.addAll(lavaCubes)
        var sides = 0L
        lavaCubes.forEach { cube ->
            cube.neighbours().forEach { neighbour ->
                if (!lava.contains(neighbour) &&
                    (air.contains(neighbour) || neighbour.isOut(minEdge, maxEdge) ||
                        reachesTheEdge(lava, air, neighbour, minEdge, maxEdge))
                ) {
                    println("$neighbour is outside")
                    air.add(neighbour)
                    sides++
                }
            }
        }
        return sides
    }

    private fun reachesTheEdge(lava: HashSet<Cube>, air: HashSet<Cube>, start: Cube, min: Cube, max: Cube): Boolean {
        val visited = hashSetOf(start)
        var ongoing = hashSetOf(start)
        while (ongoing.isNotEmpty()) {
            val newOngoing = hashSetOf<Cube>()
            ongoing.forEach { cube ->
                cube.neighbours().forEach { other ->
                    if (other.isOut(min, max)) {
                        air.addAll(visited)
                        return true
                    } else if (!lava.contains(other) && visited.add(other)) {
                        newOngoing.add(other)
                    }
                }
            }
            ongoing = newOngoing
        }
        println("$start is inside")
        lava.addAll(visited)
        return false
    }

    data class Cube(val x: Int, val y: Int, val z: Int) {
        fun isTouching(other: Cube): Boolean =
            ((x - other.x).absoluteValue + (y - other.y).absoluteValue + (z - other.z).absoluteValue) == 1

        operator fun minus(other: Cube): Cube = Cube(x - other.x, y - other.y, z - other.z)

        fun isOut(min: Cube, max: Cube): Boolean =
            x < min.x || x > max.x || y < min.y || y > max.y || z < min.z || z > max.z

        fun neighbours(): Sequence<Cube> = sequence {
            yield(copy(x = x - 1))
            yield(copy(x = x + 1))
            yield(copy(y = y - 1))
            yield(copy(y = y + 1))
            yield(copy(z = z - 1))
            yield(copy(z = z + 1))
        }
    }
}
