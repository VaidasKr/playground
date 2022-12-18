package advent.year2022

class Day17(input: String) {
    private val width = 7
    private val operations = input.map { if (it == '<') -1 else 1 }
    val figures = arrayOf(
        arrayOf(booleanArrayOf(true, true, true, true)),
        arrayOf(
            booleanArrayOf(false, true, false),
            booleanArrayOf(true, true, true),
            booleanArrayOf(false, true, false)
        ),
        arrayOf(
            booleanArrayOf(false, false, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(true, true, true)
        ),
        arrayOf(
            booleanArrayOf(true),
            booleanArrayOf(true),
            booleanArrayOf(true),
            booleanArrayOf(true)
        ),
        arrayOf(
            booleanArrayOf(true, true),
            booleanArrayOf(true, true)
        )
    )

    fun runWithBuffer(rocks: Long, bufferSize: Int, repeatCheck: Boolean = false): Long {
        var dropped = 0L
        val buffer = Array(bufferSize + 5) { BooleanArray(width) }
        var height = 0L
        val startOffsetX = 2
        val startOffsetY = 3
        var figureIndex = 0
        var opIndex = 0
        val lastDif = longArrayOf(0, 0)
        val cache = longArrayOf(0, 0)
        var cacheOffset = 0L
        while (dropped < rocks) {
            var figureOffset = startOffsetX
            var figureBottom = startOffsetY + height
            val figure = figures[figureIndex]
            while (true) {
                if (opIndex == 0 && figureIndex == 0) {
                    val prevHeight = cache[0]
                    val prevDropped = cache[1]
                    cache[0] = height
                    cache[1] = dropped
                    val heightDif = height - prevHeight
                    val dropDif = dropped - prevDropped
                    println(
                        "operation and figure iteration height: $height (+$heightDif) dropped: $dropped (+$dropDif) " +
                            "offset ($figureOffset $figureBottom) cacheIndex ${buffer.adjustedIndex(height)}"
                    )
                    if (repeatCheck && heightDif > 0 && dropDif > 0 && heightDif == lastDif[0] && dropDif == lastDif[1]) {
                        val times = (rocks - dropped) / dropDif
                        println("times to drop $times")
                        dropped += times * dropDif
                        height += times * heightDif
                        figureBottom += times * heightDif
                        cacheOffset = -times * heightDif
                        println(
                            "after update height $height dropped $dropped offset ($figureOffset $figureBottom) cacheIndex ${
                                buffer.adjustedIndex(
                                    height + cacheOffset
                                )
                            }"
                        )
                    } else {
                        lastDif[0] = heightDif
                        lastDif[1] = dropDif
                    }
                }
                val operation = operations[opIndex]
                opIndex = (opIndex + 1) % operations.size
                if (!intersects(figure, buffer, height, cacheOffset, figureOffset + operation, figureBottom)) {
                    figureOffset += operation
                }
                if (!intersects(figure, buffer, height, cacheOffset, figureOffset, figureBottom - 1)) {
                    figureBottom--
                } else {
                    height = addToBuffer(figure, buffer, height, cacheOffset, figureOffset, figureBottom)
                    break
                }
            }
            figureIndex = (figureIndex + 1) % figures.size
            dropped++
        }
        return height
    }

    fun intersects(
        figure: Array<BooleanArray>,
        buffer: Array<BooleanArray>,
        height: Long,
        cacheOffset: Long,
        figureOffset: Int,
        figureBottomOffset: Long
    ): Boolean {
        if (figureOffset < 0 || figureBottomOffset < 0) return true
        val width = figure.width()
        if (figureOffset + width > this.width) return true
        if (figureBottomOffset > height - 1) return false
        val figureHeight = figure.height()
        for (yI in 0 until figureHeight) {
            val lineNumber = figureBottomOffset + yI
            if (lineNumber > height - 1) continue
            val tableLine = buffer.getBuffer(lineNumber + cacheOffset)
            val figureYIndex = figureHeight - yI - 1
            val figureLine = figure[figureYIndex]
            for (figureX in 0 until width) {
                val globalX = figureX + figureOffset
                val tableValue = tableLine[globalX]
                val figureValue = figureLine[figureX]
                if (tableValue && figureValue) return true
            }
        }
        return false
    }

    private fun Array<BooleanArray>.getBuffer(index: Long): BooleanArray = this[adjustedIndex(index)]

    private fun Array<BooleanArray>.adjustedIndex(index: Long): Int = (index % size).toInt()

    private fun Array<BooleanArray>.width(): Int = first().size

    private fun Array<BooleanArray>.height(): Int = size

    private fun addToBuffer(
        figure: Array<BooleanArray>,
        buffer: Array<BooleanArray>,
        height: Long,
        cacheOffset: Long,
        figureOffset: Int,
        figureBottomOffset: Long
    ): Long {
        val width = figure.width()
        val figureHeight = figure.height()
        var extraHeight = 0
        for (yI in 0 until figureHeight) {
            val lineNumber = figureBottomOffset + yI
            if (lineNumber > height - 1) {
                extraHeight++
            }
            val tableLine = buffer.getBuffer(lineNumber + cacheOffset)
            val figureYIndex = figureHeight - yI - 1
            val figureLine = figure[figureYIndex]
            for (figureX in 0 until width) {
                if (figureLine[figureX]) tableLine[figureX + figureOffset] = true
            }
        }
        val newHeight = height + extraHeight
        for (i in newHeight until newHeight + 5) {
            val bufferLine = buffer.getBuffer(i + cacheOffset)
            for (x in bufferLine.indices) bufferLine[x] = false
        }
        return newHeight
    }
}
