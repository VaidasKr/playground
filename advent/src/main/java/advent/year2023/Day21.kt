package advent.year2023


object Day21 {
    fun possiblePositionsAfterSteps(input: String, steps: Int): Int {
        val trimmed = input.trim()
        val singleLine = trimmed.replace("\n", "")
        var start = -1
        val states = BooleanArray(singleLine.length) { index ->
            when (singleLine[index]) {
                'S' -> {
                    start = index
                    true
                }

                '#' -> false
                else -> true
            }
        }
        if (start == -1) error("no start")
        val width = trimmed.indexOf('\n')
        val map = Grid(width, singleLine.length / width, states)
        return possiblePositionsAfterStepsFrom(
            map,
            steps,
            start
        )
    }

    private class Grid(val width: Int, val height: Int, val states: BooleanArray)

    private fun possiblePositionsAfterStepsFrom(grid: Grid, steps: Int, position: Int): Int {
        var index = 0
        val maxCount = grid.states.count { it }
        var positions = hashSetOf(position)
        while (index++ < steps) {
            val newSet = hashSetOf<Int>()
            positions.forEach { pos ->
                addIfValid(grid, pos, 1, 0, newSet)
                addIfValid(grid, pos, -1, 0, newSet)
                addIfValid(grid, pos, 0, 1, newSet)
                addIfValid(grid, pos, 0, -1, newSet)
            }
            if (newSet.size == maxCount) {
                return maxCount
            }
            positions = newSet
        }
        return positions.size
    }

    private fun addIfValid(map: Grid, pos: Int, offsetX: Int, offsetY: Int, destination: HashSet<Int>) {
        val x = pos % map.width + offsetX
        val y = pos / map.width + offsetY
        val newPos = y * map.width + x
        if (x in 0 until map.width && y in 0 until map.height && map.states[newPos]) {
            destination.add(newPos)
        }
    }

    private data class InfinitePos(val position: Int, val gridX: Int, val gridY: Int)

    fun possiblePositionsAfterStepsInfinite(input: String, steps: Int): Long {
        val trimmed = input.trim()
        val singleLine = trimmed.replace("\n", "")
        var start = -1
        val states = BooleanArray(singleLine.length) { index ->
            when (singleLine[index]) {
                'S' -> {
                    start = index
                    true
                }

                '#' -> false
                else -> true
            }
        }
        if (start == -1) error("no start")
        val width = trimmed.indexOf('\n')
        val grid = Grid(width, singleLine.length / width, states)
        val startPoint = InfinitePos(start, 0, 0)
        var reachable = listOf(startPoint)
        val reached = hashSetOf<InfinitePos>()
        var sum = 0L
        var lastSum = 0L
        var lastDif = 0L
        var lastDifDif = 0L
        val evenMatch = steps % 2
        var index = 0
        while (index++ < steps) {
            val newPoints = ArrayList<InfinitePos>()
            val onValid: (InfinitePos) -> Unit = { pos ->
                if (reached.add(pos)) {
                    newPoints.add(pos)
                }
            }
            for (i in reachable.indices) {
                val pos = reachable[i]
                addIfValidInfinite(grid, pos, 1, 0, onValid)
                addIfValidInfinite(grid, pos, 0, 1, onValid)
                addIfValidInfinite(grid, pos, -1, 0, onValid)
                addIfValidInfinite(grid, pos, 0, -1, onValid)
            }
            if (index % 2 == evenMatch) {
                sum += newPoints.size
            }
            if (index % 262 == 65) {
                val dif = sum - lastSum
                lastSum = sum
                val difDif = dif - lastDif
                lastDif = dif
                if (lastDifDif > 0 && lastDifDif == difDif) {
                    while (index < steps) {
                        index += 262
                        lastDif += lastDifDif
                        sum += lastDif
                    }
                    return sum
                }
                lastDifDif = difDif
            }
            reachable = newPoints
        }
        return sum
    }

    private inline fun addIfValidInfinite(
        grid: Grid,
        pos: InfinitePos,
        offsetX: Int,
        offsetY: Int,
        onValid: (InfinitePos) -> Unit
    ) {
        var x = pos.position % grid.width + offsetX
        var y = pos.position / grid.width + offsetY
        var gridX = pos.gridX
        var gridY = pos.gridY
        if (x < 0) {
            x = grid.width - 1
            gridX--
        } else if (x == grid.width) {
            x = 0
            gridX++
        }
        if (y < 0) {
            y = grid.height - 1
            gridY--
        } else if (y == grid.height) {
            y = 0
            gridY++
        }
        val newPos = y * grid.width + x
        if (grid.states[newPos]) onValid(InfinitePos(newPos, gridX, gridY))
    }
}
