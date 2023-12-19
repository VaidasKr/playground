package advent.year2023

object Day19 {
    fun sumOfAcceptedParts(input: String): Long {
        val workflows = hashMapOf<String, List<String>>()
        val ratings = mutableListOf<Map<String, Int>>()
        var collectRatings = false
        input.split('\n').forEach { line ->
            if (line.isBlank()) {
                collectRatings = true
            } else if (collectRatings) {
                ratings.add(buildMap {
                    line.substring(1, line.length - 1).split(',').forEach { part ->
                        val (name, value) = part.split('=')
                        put(name, value.toInt())
                    }
                })
            } else {
                val endIndex = line.indexOf('{')
                val name = line.substring(0, endIndex)
                workflows[name] = line.substring(endIndex + 1, line.length - 1).split(',')
            }
        }
        return sumOfAcceptedParts(workflows, ratings)
    }

    private fun sumOfAcceptedParts(workflows: Map<String, List<String>>, ratings: List<Map<String, Int>>): Long {
        var sum = 0L
        ratings.forEach { partValues ->
            if (isAccepted("in", workflows, partValues)) {
                partValues.forEach { (_, value) -> sum += value }
            }
        }
        return sum
    }

    private fun isAccepted(block: String, workflows: Map<String, List<String>>, ratings: Map<String, Int>): Boolean {
        if (block == "A") return true
        if (block == "R") return false
        val rules = workflows[block]!!
        rules.forEach { rule ->
            if (rule.contains('<')) {
                val (name, second) = rule.split('<')
                val (value, otherWorkFlow) = second.split(':')
                if (ratings[name]!! < value.toInt()) {
                    return isAccepted(otherWorkFlow, workflows, ratings)
                }
            } else if (rule.contains('>')) {
                val (name, second) = rule.split('>')
                val (value, otherWorkFlow) = second.split(':')
                if (ratings[name]!! > value.toInt()) {
                    return isAccepted(otherWorkFlow, workflows, ratings)
                }
            } else {
                return isAccepted(rule, workflows, ratings)
            }
        }
        return false
    }

    fun possibleCombinations(input: String): Long {
        val workflows = hashMapOf<String, List<String>>()
        input.split('\n').forEach { line ->
            if (line.isBlank()) return@forEach
            val endIndex = line.indexOf('{')
            val name = line.substring(0, endIndex)
            workflows[name] = line.substring(endIndex + 1, line.length - 1).split(',')
        }
        val ratings = mapOf("s" to (1 to 4000), "x" to (1 to 4000), "m" to (1 to 4000), "a" to (1 to 4000))

        return calculateRanges("in", workflows, ratings)
    }

    private fun calculateRanges(
        block: String,
        workflows: Map<String, List<String>>,
        ratings: Map<String, Pair<Int, Int>>
    ): Long {
        if (block == "R" || ratings.any { (_, value) -> (value.second - value.first) < 0 }) return 0
        if (block == "A") return possibleCombinations(ratings)
        val rules = workflows[block]!!
        var leftOver = ratings
        return rules.sumOf { rule ->
            if (rule.contains('<')) {
                val (name, second) = rule.split('<')
                val (value, otherWorkFlow) = second.split(':')
                val range = leftOver[name]!!
                val intVal = value.toInt()
                val mutable = leftOver.toMutableMap()
                mutable[name] = intVal to range.second
                leftOver = mutable.toMap()
                mutable[name] = range.first to intVal - 1
                calculateRanges(otherWorkFlow, workflows, mutable.toMap())
            } else if (rule.contains('>')) {
                val (name, second) = rule.split('>')
                val (value, otherWorkFlow) = second.split(':')
                val range = leftOver[name]!!
                val intVal = value.toInt()
                val mutable = leftOver.toMutableMap()
                mutable[name] = range.first to intVal
                leftOver = mutable.toMap()
                mutable[name] = intVal + 1 to range.second
                calculateRanges(otherWorkFlow, workflows, mutable.toMap())
            } else {
                calculateRanges(rule, workflows, leftOver)
            }
        }
    }

    private fun possibleCombinations(ratings: Map<String, Pair<Int, Int>>): Long {
        var power = 1L
        ratings.forEach { (_, range) ->
            power *= (range.second - range.first + 1)
        }
        return power
    }
}
