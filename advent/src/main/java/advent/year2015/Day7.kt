package advent.year2015

object Day7 {
    fun toValueMap(input: String): Map<String, UInt> = buildMapFromList(input.split("\n").map { it.trim() })

    private fun buildMapFromList(list: List<String>): Map<String, UInt> {
        val lines = list.toMutableList()
        return buildMap {
            while (lines.isNotEmpty()) {
                val line = lines.removeFirst()
                val lastSpace = line.indexOfLast { char -> char == ' ' }
                val last = line.substring(lastSpace + 1, line.length)
                if (line.contains("NOT")) {
                    parseOrKeep(lines, line, last, getValueAfter(line, "NOT")) { it xor 65535u }
                } else if (line.contains("AND")) {
                    parseOrKeep(lines, line, last, "AND") { first, second -> first and second }
                } else if (line.contains("OR")) {
                    parseOrKeep(lines, line, last, "OR") { first, second -> first or second }
                } else if (line.contains("LSHIFT")) {
                    parseOrKeep(lines, line, last, "LSHIFT") { first, second -> first shl second.toInt() }
                } else if (line.contains("RSHIFT")) {
                    parseOrKeep(lines, line, last, "RSHIFT") { first, second -> first shr second.toInt() }
                } else {
                    parseOrKeep(lines, line, last, firstPart(line)) { it }
                }
            }
        }
    }

    private fun MutableMap<String, UInt>.parseOrKeep(
        lines: MutableList<String>, line: String, key: String, number: UInt?, op: (UInt) -> UInt
    ) {
        if (number == null) lines.add(line) else put(key, op(number))
    }

    private fun MutableMap<String, UInt>.parseOrKeep(
        lines: MutableList<String>, line: String, last: String, div: String, op: (UInt, UInt) -> UInt
    ) {
        val first = firstPart(line)
        val second = getValueAfter(line, div)
        if (first == null || second == null) lines.add(line) else put(last, op(first, second))
    }

    private fun MutableMap<String, UInt>.firstPart(line: String): UInt? {
        val part = line.substring(0, line.indexOf(' '))
        return part.toUIntOrNull() ?: get(part)
    }

    private fun MutableMap<String, UInt>.getValueAfter(line: String, after: String): UInt? {
        val part = line.substring(line.indexOf(after) + after.length + 1, line.indexOf('-') - 1)
        return part.toUIntOrNull() ?: get(part)
    }

    fun toValueMapWithOverride(input: String, overrideKey: String, overrideValue: String): Map<String, UInt> {
        val lines = input.split("\n").map { it.trim() }
        val lineToReplace = lines.indexOfFirst { it.endsWith("-> $overrideKey") }
        val newLine = "$overrideValue -> b"
        val updated = lines.toMutableList()
        updated.removeAt(lineToReplace)
        updated.add(newLine)
        return buildMapFromList(updated)
    }
}
