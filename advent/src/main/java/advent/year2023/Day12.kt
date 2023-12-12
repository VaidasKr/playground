package advent.year2023

object Day12 {
    fun sumOfPossibleArrangements(input: String): Long {
        val memory: HashMap<Pair<String, List<Int>>, Long> = hashMapOf()
        return input.trim().split('\n')
            .sumOf { line -> parse(line) { records, sequence -> solve(memory, records, sequence) } }
    }

    private inline fun parse(line: String, parsed: (String, List<Int>) -> Long): Long {
        val (records, damaged) = line.split(' ')
        return parsed(records, damaged.split(',').map { it.toInt() })
    }

    private fun solve(
        memory: HashMap<Pair<String, List<Int>>, Long>,
        records: String,
        brokenSequence: List<Int>
    ): Long {
        if (records.isEmpty()) return if (brokenSequence.isEmpty()) 1 else 0
        return when (records.first()) {
            '.' -> solve(memory, records.drop(1), brokenSequence)
            '#' -> checkSequence(memory, records, brokenSequence)
            '?' -> solve(memory, records.drop(1), brokenSequence) + checkSequence(memory, records, brokenSequence)
            else -> throw RuntimeException("fail")
        }
    }

    private fun checkSequence(
        memory: HashMap<Pair<String, List<Int>>, Long>,
        records: String,
        brokenSequence: List<Int>
    ): Long = memory.getOrPut(records to brokenSequence) {
        if (brokenSequence.isEmpty()) return 0
        val sequenceSize = brokenSequence.first()
        if (records.length < sequenceSize) return 0
        for (i in 0 until sequenceSize) {
            if (records[i] == '.') return 0
        }
        if (records.length == sequenceSize) {
            if (brokenSequence.size == 1) return 1
            return 0
        }
        if (records[sequenceSize] == '#') return 0
        solve(memory, records.drop(sequenceSize + 1), brokenSequence.drop(1))
    }

    fun sumOfPossibleArrangementsUnfolded(input: String): Long {
        val memory: HashMap<Pair<String, List<Int>>, Long> = hashMapOf()
        return input.trim().split('\n')
            .sumOf { line -> parse(line) { records, sequence -> unfoldAndSolve(memory, records, sequence) } }
    }

    private fun unfoldAndSolve(
        memory: HashMap<Pair<String, List<Int>>, Long>,
        records: String,
        brokenSequence: List<Int>
    ): Long {
        val unfoldedRecords = StringBuilder(records.length * 5 + 4).append(records)
        val unfoldedSequence = ArrayList<Int>(brokenSequence.size * 5)
        unfoldedSequence.addAll(brokenSequence)
        repeat(4) {
            unfoldedRecords.append('?').append(records)
            unfoldedSequence.addAll(brokenSequence)
        }
        return solve(memory, unfoldedRecords.toString(), unfoldedSequence)
    }
}
