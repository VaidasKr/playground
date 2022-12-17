package advent.year2022

class Day16(input: String) {
    private val valves: List<Valve>
    private val nodes: Map<String, Node>

    init {
        val valves = input.split("\n").map { line ->
            val name = line.substring(6, 8)
            val endIndex = line.indexOf(';')
            val rate = line.substring(line.indexOf('=') + 1, endIndex)
            val neighbors = line.substring(line.indexOf(' ', endIndex + 23), line.length).trim().split(", ")
            Valve(name, rate.toInt(), neighbors)
        }
        this.valves = valves
        val valveMap = valves.associateBy { it.name }
        val nodes = mutableListOf<Node>()
        valves.mapTo(nodes) { valve ->
            val visited = hashSetOf(valve.name)
            val roads = mutableListOf<Road>()
            var distance = 2
            nodes.forEach { node ->
                visited.add(node.name)
                roads.add(node.roads.first { road -> road.to == valve.name }.reverse)
            }
            var ongoing = hashSetOf(valve.name)
            while (visited.size < valves.size) {
                val newOngoing = hashSetOf<String>()
                ongoing.forEach { valveName ->
                    val ongoingValve = valveMap[valveName]!!
                    ongoingValve.tunnelsTo.forEach { destination ->
                        if (visited.add(destination)) {
                            roads.add(Road(valve.name, destination, distance))
                        }
                        newOngoing.add(destination)
                    }
                }
                ongoing = newOngoing
                distance++
            }
            Node(valve.name, valve.flow, roads)
        }
        val nodesAndRoadsToKeep = valves.filter { it.flow > 0 || it.name == "AA" }.map { it.name }.toSet()
        this.nodes = nodes
            .mapNotNull { node -> filterNode(node, nodesAndRoadsToKeep) }
            .associateBy { it.name }
    }

    private fun filterNode(node: Node, nodesAndRoadsToKeep: Set<String>): Node? {
        if (!nodesAndRoadsToKeep.contains(node.name)) return null
        val roads = node.roads.filter { road -> nodesAndRoadsToKeep.contains(road.to) }
        return node.copy(roads = roads)
    }

    data class Node(val name: String, val flow: Int, val roads: List<Road>)

    data class Road(val from: String, val to: String, val distance: Int) {
        val reverse: Road get() = Road(to, from, distance)
    }


    fun mostPressureScore(time: Int): Int {
        var ongoing = hashSetOf(SinglePath(time, 0, "AA", "AA"))
        var max = 0
        while (ongoing.isNotEmpty()) {
            val newOngoing = hashSetOf<SinglePath>()
            ongoing.forEach { singlePath ->
                singlePath.suggested(nodes) { suggested ->
                    if (suggested.score > max) {
                        max = suggested.score
                    }
                    newOngoing.add(suggested)
                }
            }
            ongoing = newOngoing
        }
        return max
    }

    fun mostPressureScore2Runners(time: Int): Int {
        var ongoing = hashSetOf(MultiPath(time, "AA", time, "AA", 0, "AA"))
        var max = 0
        while (ongoing.isNotEmpty()) {
            val newOngoing = hashSetOf<MultiPath>()
            var newMax = max
            ongoing.forEach { singlePath ->
                singlePath.suggested(nodes) { suggested ->
                    if (suggested.score > newMax) {
                        newMax = suggested.score
                    }
                    if (suggested.score > max) {
                        newOngoing.add(suggested)
                    }
                }
            }
            max = newMax
            ongoing = newOngoing
        }
        return max
    }

    data class MultiPath(
        val firstTime: Int,
        val firstPos: String,
        val secondTime: Int,
        val secondPos: String,
        val score: Int,
        val opened: String
    ) {
        fun suggested(map: Map<String, Node>, onNewPath: (MultiPath) -> Unit) {
            val nodesToGetTo = map.keys.filter { opened.isMissingPair(it) }
            val firstNode = map[firstPos]!!
            val secondNode = map[secondPos]!!
            val firstOptions = mutableListOf<Pair<Node, Int>>()
            val secondOptions = mutableListOf<Pair<Node, Int>>()
            nodesToGetTo.forEach { nodeName ->
                val node = map[nodeName]!!
                val distanceToFirst = firstNode.roads.first { road -> road.to == nodeName }.distance
                val distanceToSecond = secondNode.roads.first { road -> road.to == nodeName }.distance
                if (distanceToFirst <= firstTime && firstTime > 0) {
                    firstOptions.add(node to firstTime - distanceToFirst)
                }
                if (distanceToSecond <= secondTime && secondTime > 0) {
                    secondOptions.add(node to secondTime - distanceToSecond)
                }
            }
            if (firstOptions.isEmpty() && secondOptions.isEmpty()) return
            if (firstOptions.isEmpty()) {
                secondOptions.forEach { (node, time) ->
                    onNewPath(
                        MultiPath(
                            0,
                            firstPos,
                            time,
                            node.name,
                            score + time * node.flow,
                            opened + node.name
                        )
                    )
                }
            } else if (secondOptions.isEmpty()) {
                firstOptions.forEach { (node, time) ->
                    onNewPath(
                        MultiPath(
                            time, node.name,
                            0, secondPos,
                            score + time * node.flow,
                            opened + node.name
                        )
                    )
                }
            } else {
                firstOptions.forEach { (firstDes, firstTime) ->
                    val set = opened + firstDes.name
                    val score = score + firstTime * firstDes.flow
                    secondOptions.forEach { (secondDes, secondTime) ->
                        if (firstDes.name != secondDes.name) {
                            onNewPath(
                                MultiPath(
                                    firstTime,
                                    firstDes.name,
                                    secondTime,
                                    secondDes.name,
                                    score + secondTime * secondDes.flow,
                                    set + secondDes.name
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    data class SinglePath(val time: Int, val score: Int, val position: String, val opened: String) {
        inline fun suggested(map: Map<String, Node>, onNewPath: (SinglePath) -> Unit) {
            val current = map[position]!!
            current.roads.forEach { road ->
                val openTime = time - road.distance
                if (opened.isMissingPair(road.to) && openTime > 0) {
                    val destination = map[road.to]!!
                    onNewPath(
                        SinglePath(
                            openTime,
                            score + openTime * destination.flow,
                            road.to,
                            opened + road.to
                        )
                    )
                }
            }
        }
    }

    data class Valve(val name: String, val flow: Int, val tunnelsTo: List<String>)

}

fun String.isMissingPair(other: String): Boolean {
    for (i in 0 until length step 2) {
        if (get(i) == other[0] && get(i + 1) == other[1]) return false
    }
    return true
}
