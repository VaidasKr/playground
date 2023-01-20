package advent.year2015

import java.util.concurrent.atomic.AtomicInteger

class Day19 {
    private val mutations = hashMapOf<String, MutableList<String>>()

    fun add(from: String, to: String) {
        mutations.getOrPut(from) { mutableListOf() }.add(to)
    }

    fun mutationPossibilities(source: String): Set<String> = buildSet { source.onMutation { add(it) } }

    private inline fun String.onMutation(onMutation: (String) -> Unit) {
        val indices = indices
        val keys = mutations.keys
        for (i in indices) {
            keys.forEach { key ->
                if (i + key.length <= length) {
                    for (k in key.indices) {
                        val mutationChar = key[k]
                        if (mutationChar != get(i + k)) {
                            return@forEach
                        }
                    }
                    mutations[key].orEmpty().forEach { to ->
                        onMutation(substring(0, i) + to + substring(i + key.length))
                    }
                }
            }
        }
    }

    fun mutationCount(from: String, to: String): Int {
        var ongoing = hashSetOf(from)
        var steps = 0
        while (ongoing.isNotEmpty()) {
            steps++
            val newOngoing = hashSetOf<String>()
            ongoing.forEach { line ->
                line.onMutation { mutation ->
                    if (mutation == to) return steps
                    newOngoing.add(mutation)
                }
            }
            ongoing = newOngoing
        }
        return Int.MAX_VALUE
    }

    fun reversedMutations(from: String, to: String): Int {
        val reversed = reversed()
        var arIndex = to.indexOf("Ar")
        var mutated = to
        var end: Int
        val counter = AtomicInteger()
        while (arIndex != -1) {
            end = arIndex + 2
            val molecule = mutated.substring(0, end)
            val replacement = reversed.parseWhileAvailable(counter, molecule)
            mutated = replacement.first() + mutated.substring(end)
            arIndex = mutated.indexOf("Ar")
        }
        return if (mutated != from) counter.get() + mutationCount(from, mutated) else counter.get()
    }

    fun reversed(): Day19 =
        mutations.asSequence().fold(Day19()) { day, (key, value) -> day.apply { value.forEach { add(it, key) } } }

    private fun parseWhileAvailable(counter: AtomicInteger, molecule: String): Set<String> {
        var ongoing = hashSetOf(molecule)
        while (true) {
            val newOngoing = hashSetOf<String>()
            ongoing.forEach { line ->
                line.onMutation { mutation ->
                    newOngoing.add(mutation)
                }
            }
            if (newOngoing.isEmpty()) return ongoing
            counter.incrementAndGet()
            ongoing = newOngoing
        }
    }

    companion object {
        fun parse(input: String, onResult: (Day19, String) -> Unit) {
            val day19 = Day19()
            val lines = input.lineSequence().toMutableList()
            val source = lines.removeLast()
            lines.forEach { line ->
                if (line.isNotBlank()) {
                    day19.add(line.substring(0, line.indexOf(' ')), line.substring(line.indexOfLast { it == ' ' } + 1))
                }
            }
            onResult(day19, source)
        }
    }
}
