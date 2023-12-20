package advent.year2023

object Day20 {
    fun pulseMultiplicationAfter(input: String, buttonPresses: Int): Long {
        val connectionMap = hashMapOf<String, List<String>>()
        val sources = hashMapOf<String, ArrayList<String>>()
        val conjunctions = mutableListOf<String>()
        var signals = emptyList<Signal>()
        input.split('\n').forEach { line ->
            val (moduleWithType, connections) = line.split(" -> ")
            if (moduleWithType == "broadcaster") {
                signals = connections.split(", ").map { Signal("broadcaster", it, false) }
            } else {
                val type = moduleWithType.first()
                val substring = moduleWithType.substring(1)
                if (type == '&') {
                    conjunctions.add(substring)
                } else if (type != '%') {
                    error("unknown type $line")
                }
                val connectionList = connections.split(", ")
                connectionMap[substring] = connectionList
                connectionList.forEach { connection ->
                    sources.getOrPut(connection) { ArrayList() }.add(substring)
                }
            }
        }
        val moduleMap = buildMap {
            connectionMap.forEach { (name, destinations) ->
                val module = if (conjunctions.contains(name)) {
                    Conjunction(name, destinations, sources[name]!!.toSet())
                } else {
                    FlipFlop(name, destinations)
                }
                put(name, module)
            }
        }
        var highSignals = 0L
        var lowSignals = 0L
        var index = 0
        while (index++ < buttonPresses) {
            lowSignals++
            var sending = signals
            while (sending.isNotEmpty()) {
                val newSignals = mutableListOf<Signal>()
                sending.forEach { signal ->
                    if (signal.high) {
                        highSignals++
                    } else {
                        lowSignals++
                    }
                    if (signal.to == "rx" && !signal.high) {
                        return index.toLong()
                    }
                    moduleMap[signal.to]?.apply {
                        newSignals.addAll(send(signal))
                    }
                }
                sending = newSignals
            }
        }
        return highSignals * lowSignals
    }

    fun findPressedToRx(input: String): Long {
        val connectionMap = hashMapOf<String, List<String>>()
        val sources = hashMapOf<String, ArrayList<String>>()
        val conjunctions = mutableListOf<String>()
        var signals = emptyList<Signal>()
        input.split('\n').forEach { line ->
            val (moduleWithType, connections) = line.split(" -> ")
            if (moduleWithType == "broadcaster") {
                signals = connections.split(", ").map { Signal("broadcaster", it, false) }
            } else {
                val type = moduleWithType.first()
                val substring = moduleWithType.substring(1)
                if (type == '&') {
                    conjunctions.add(substring)
                } else if (type != '%') {
                    error("unknown type $line")
                }
                val connectionList = connections.split(", ")
                connectionMap[substring] = connectionList
                connectionList.forEach { connection ->
                    sources.getOrPut(connection) { ArrayList() }.add(substring)
                }
            }
        }
        val rxSource = sources["rx"]!!.first()
        val sourcesToFind = sources[rxSource]!!.toHashSet()
        val variables = hashSetOf<Int>()
        val moduleMap = buildMap {
            connectionMap.forEach { (name, destinations) ->
                val module = if (conjunctions.contains(name)) {
                    Conjunction(name, destinations, sources[name]!!.toSet())
                } else {
                    FlipFlop(name, destinations)
                }
                put(name, module)
            }
        }
        var index = 0
        while (true) {
            index++
            var sending = signals
            while (sending.isNotEmpty()) {
                val newSignals = mutableListOf<Signal>()
                sending.forEach { signal ->
                    if (signal.to == rxSource && signal.high) {
                        if (sourcesToFind.remove(signal.from)) {
                            variables.add(index)
                        }
                        if (sourcesToFind.isEmpty()) {
                            return variables.leastCommonMultipleOf()
                        }
                    }
                    if (signal.to == "rx" && !signal.high) {
                        return index.toLong()
                    }
                    moduleMap[signal.to]?.apply {
                        newSignals.addAll(send(signal))
                    }
                }
                sending = newSignals
            }
        }
    }

    private fun Set<Int>.leastCommonMultipleOf(): Long =
        fold(1L) { acc, value -> lowestCommonMultiple(acc, value.toLong()) }

    private fun lowestCommonMultiple(a: Long, b: Long): Long = a * (b / greatestCommonDivider(a, b))

    private fun greatestCommonDivider(a: Long, b: Long): Long {
        var calcA = a
        var calcB = b
        while (calcB > 0) {
            val temp = calcB
            calcB = calcA % calcB
            calcA = temp
        }
        return calcA
    }

    private interface Module {
        val name: String

        val isInitialState: Boolean

        fun send(signal: Signal): List<Signal>
    }

    private class FlipFlop(
        override val name: String,
        private val destinations: List<String>
    ) : Module {
        var isOn: Boolean = false

        override fun send(signal: Signal): List<Signal> = if (signal.high) {
            emptyList()
        } else {
            isOn = !isOn
            destinations.map { Signal(name, it, isOn) }
        }

        override val isInitialState: Boolean get() = !isOn
    }

    private class Conjunction(
        override val name: String,
        val destinations: List<String>,
        sources: Set<String>
    ) : Module {
        private val state = hashMapOf<String, Boolean>()

        init {
            sources.forEach {
                state[it] = false
            }
        }

        override val isInitialState: Boolean
            get() = state.none { (_, high) -> high }

        override fun send(signal: Signal): List<Signal> {
            state[signal.from] = signal.high
            val high = !state.all { (_, high) -> high }
            return destinations.map { Signal(name, it, high) }
        }
    }

    private data class Signal(val from: String, val to: String, val high: Boolean)
}
