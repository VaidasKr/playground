package advent.year2015

class Day9(input: String) {
    private val nodes: Map<String, Set<Path>>

    init {
        val nodes = hashMapOf<String, HashSet<Path>>()
        input.split("\n").forEach { line ->
            val words = line.trim().split(' ')
            val from = words[0]
            val to = words[2]
            val distance = words[4].toInt()
            nodes.getOrPut(from) { HashSet() }.add(Path(to, distance))
            nodes.getOrPut(to) { HashSet() }.add(Path(from, distance))
        }
        this.nodes = nodes
    }

    fun shortestToVisitAll(): Int = onVisit(Int.MAX_VALUE) { acc, next -> acc.coerceAtMost(next) }

    fun longestPathToVisitAll(): Int = onVisit(0) { acc, next -> acc.coerceAtLeast(next) }

    private inline fun onVisit(startingVal: Int, fold: (Int, Int) -> Int): Int {
        val locations = nodes.keys
        var result = startingVal
        locations.take(locations.size / 2).forEach { location ->
            result = fold(result, onVisitAll(location, locations.size, result, fold))
        }
        return result
    }

    private inline fun onVisitAll(start: String, visitCount: Int, startingVal: Int, fold: (Int, Int) -> Int): Int {
        var ongoing = hashSetOf(State(start, 0, setOf(start)))
        var result = startingVal
        while (ongoing.isNotEmpty()) {
            val newOngoing = hashSetOf<State>()
            ongoing.forEach { state ->
                val paths = nodes[state.location].orEmpty()
                paths.forEach { path ->
                    if (!state.visited.contains(path.destination)) {
                        val next = state.go(path)
                        if (next.visited.size == visitCount) {
                            result = fold(result, next.travelled)
                        } else {
                            newOngoing.add(next)
                        }
                    }
                }
            }
            ongoing = newOngoing
        }
        return result
    }

    private data class Path(val destination: String, val distance: Int)

    private data class State(val location: String, val travelled: Int, val visited: Set<String>) {
        fun go(path: Path): State = State(path.destination, travelled + path.distance, visited + path.destination)
    }
}
