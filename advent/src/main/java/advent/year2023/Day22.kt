package advent.year2023

object Day22 {
    fun safeToDisintegrateBricks(input: String): Int {
        val bricks = input.trim().split('\n').map { line ->
            val (start, end) = line.split('~').map { it.split(',').map { it.toInt() } }
            val points = IntArray(6)
            for (i in 0 until 3) {
                points[i] = start[i].coerceAtMost(end[i])
                points[i + 3] = start[i].coerceAtLeast(end[i])
            }
            points
        }.sortedBy { it[2] }

        val supportedList = Array<List<Int>>(bricks.size) { emptyList() }
        val brickSet = bricks.indices.toHashSet()
        for (i in bricks.indices) {
            val brick = bricks[i]
            if (brick[2] == 1) {
                continue
            }
            var highestPoint = 1
            var supportedBy = mutableListOf<Int>()
            for (j in i - 1 downTo 0) {
                val droppedBrick = bricks[j]
                if (!(droppedBrick[0] > brick[3] || droppedBrick[3] < brick[0] ||
                        droppedBrick[1] > brick[4] || droppedBrick[4] < brick[1])
                ) {
                    val supportedHeight = droppedBrick[5] + 1
                    if (supportedHeight > highestPoint) {
                        supportedBy = arrayListOf(j)
                        highestPoint = supportedHeight
                    } else if (supportedHeight == highestPoint) {
                        supportedBy.add(j)
                    }
                }
            }
            if (supportedBy.size == 1) {
                brickSet.remove(supportedBy.first())
            }
            supportedList[i] = supportedBy
            val dif = brick[2] - highestPoint
            brick[2] = brick[2] - dif
            brick[5] = brick[5] - dif
        }

        return brickSet.size
    }

    fun sumOfDropsWhenRemoved(input: String): Int {
        val bricks = input.trim().split('\n').map { line ->
            val (start, end) = line.split('~').map { it.split(',').map { it.toInt() } }
            val points = IntArray(6)
            for (i in 0 until 3) {
                points[i] = start[i].coerceAtMost(end[i])
                points[i + 3] = start[i].coerceAtLeast(end[i])
            }
            points
        }.sortedBy { it[2] }

        val supportedByList = Array<List<Int>>(bricks.size) { emptyList() }
        val supports = Array<ArrayList<Int>>(bricks.size) { ArrayList() }
        for (i in bricks.indices) {
            val brick = bricks[i]
            if (brick[2] == 1) {
                continue
            }
            var highestPoint = 0
            var supportedBy = mutableListOf<Int>()
            for (j in i - 1 downTo 0) {
                val droppedBrick = bricks[j]
                if (droppedBrick[0] > brick[3] || droppedBrick[3] < brick[0] ||
                    droppedBrick[1] > brick[4] || droppedBrick[4] < brick[1]
                ) continue
                val supportedHeight = droppedBrick[5]
                if (supportedHeight > highestPoint) {
                    supportedBy = arrayListOf(j)
                    highestPoint = supportedHeight
                } else if (supportedHeight == highestPoint) {
                    supportedBy.add(j)
                }
            }
            supportedBy.forEach { supporter ->
                supports[supporter].add(i)
            }
            supportedByList[i] = supportedBy
            val dif = brick[2] - highestPoint - 1
            brick[2] = brick[2] - dif
            brick[5] = brick[5] - dif
        }
        return bricks.indices.sumOf { index ->
            var candidateBricks = supports[index].toHashSet()
            val droppedBricks = hashSetOf(index)
            while (candidateBricks.isNotEmpty()) {
                val newBricksToDrop = hashSetOf<Int>()
                candidateBricks.forEach { brick ->
                    if (droppedBricks.containsAll(supportedByList[brick])) {
                        droppedBricks.add(brick)
                        newBricksToDrop.addAll(supports[brick])
                    }
                }
                candidateBricks = newBricksToDrop
            }
            droppedBricks.size - 1
        }
    }
}
