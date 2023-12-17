package advent.year2023

import java.util.PriorityQueue

object Day17 {
    fun findMinimalHeatLoss(input: String): Int {
        val map = input.trim().split('\n')
        val width = map.first().length
        val height = map.size
        val boundsPredicate: (State) -> Boolean = { state -> state.inBounds(width, height) }
        return findMinPathCost(
            start = State(0, 0, 0, 1, 0),
            isEnd = { state -> state.x == width - 1 && state.y == map.lastIndex },
            onNeighbour = { state, consumer: (State) -> Unit ->
                if (state.straightMoves < 3) consumer.consumeIf(state.forward(), boundsPredicate)
                consumer.consumeIf(state.left(), boundsPredicate)
                consumer.consumeIf(state.right(), boundsPredicate)
            },
            cost = { dest: State -> map[dest.y][dest.x].digitToInt() }
        )
    }

    private inline fun ((State) -> Unit).consumeIf(state: State, predicate: (State) -> Boolean) {
        if (predicate(state)) this(state)
    }

    fun findMinimalHeatLossUltra(input: String): Int {
        val map = input.trim().split('\n')
        val width = map.first().length
        val height = map.size
        val boundsPredicate: (State) -> Boolean = { state -> state.inBounds(width, height) }
        return findMinPathCost(
            start = State(0, 0, 0, 1, 0),
            isEnd = { state -> state.x == width - 1 && state.y == map.lastIndex && state.straightMoves >= 4 },
            onNeighbour = { state, consumer: (State) -> Unit ->
                if (state.straightMoves < 10) consumer.consumeIf(state.forward(), boundsPredicate)
                if (state.straightMoves >= 4 || state.straightMoves == 0) {
                    consumer.consumeIf(state.left(), boundsPredicate)
                    consumer.consumeIf(state.right(), boundsPredicate)
                }
            },
            cost = { dest: State -> map[dest.y][dest.x].digitToInt() }
        )
    }

    private inline fun findMinPathCost(
        start: State,
        isEnd: (State) -> Boolean,
        onNeighbour: (State, (State) -> Unit) -> Unit,
        crossinline cost: (State) -> Int
    ): Int {
        val visited = hashSetOf(start)
        val toVisit = PriorityQueue(listOf(ScoreState(start, 0)))

        while (toVisit.isNotEmpty()) {
            val (currentState, currentScore) = toVisit.poll()
            if (isEnd(currentState)) return currentScore

            onNeighbour(currentState) { next ->
                if (visited.add(next)) toVisit.add(ScoreState(next, currentScore + cost(next)))
            }
        }

        return Int.MAX_VALUE
    }

    private data class ScoreState(val state: State, val score: Int) : Comparable<ScoreState> {
        override fun compareTo(other: ScoreState): Int = score.compareTo(other.score)
    }

    private data class State(
        val x: Int,
        val y: Int,
        val dirX: Int,
        val dirY: Int,
        val straightMoves: Int
    ) {
        fun inBounds(width: Int, height: Int): Boolean = x in 0 until width && y in 0 until height

        fun forward() = State(x = x + dirX, y = y + dirY, dirX = dirX, dirY = dirY, straightMoves = straightMoves + 1)

        fun right() = State(x = x - dirY, y = y + dirX, dirX = -dirY, dirY = dirX, straightMoves = 1)

        fun left() = State(x = x + dirY, y = y - dirX, dirX = dirY, dirY = -dirX, straightMoves = 1)
    }
}
