package advent.year2022

object Day21 {
    fun findRootMonkeyNumber(input: String): Long {
        val numberMonkeys = hashMapOf<String, Long>()
        var monkeysWithOperation = hashSetOf<Monkey>()
        input.split("\n").forEach { monkeyLine ->
            val name = monkeyLine.take(4)
            if (monkeyLine[6].isDigit()) {
                numberMonkeys[name] = monkeyLine.substring(6, monkeyLine.length).toLong()
            } else {
                val first = monkeyLine.substring(6, 10)
                val second = monkeyLine.substring(13, monkeyLine.length)
                val op = monkeyLine[11].op()
                monkeysWithOperation.add(Monkey(name, first, second, op))
            }
        }
        while (monkeysWithOperation.isNotEmpty()) {
            val nextOperations = hashSetOf<Monkey>()
            monkeysWithOperation.forEach { monkey ->
                val firstNum = numberMonkeys[monkey.first]
                val secondNum = numberMonkeys[monkey.second]
                if (firstNum == null || secondNum == null) {
                    nextOperations.add(monkey)
                } else {
                    numberMonkeys[monkey.name] = monkey.operation.perform(firstNum, secondNum)
                }
            }
            monkeysWithOperation = nextOperations
        }
        return numberMonkeys["root"] ?: 0L
    }

    private fun Char.op(): Operation {
        return when (this) {
            '+' -> Operation.Plus
            '-' -> Operation.Minus
            '*' -> Operation.Multiply
            '/' -> Operation.Divide
            else -> throw RuntimeException("not operation char ($this)")
        }
    }

    const val human = "humn"

    fun findHumanNumber(input: String): Long {
        val numberMonkeys = hashMapOf<String, Long>()
        var monkeysWithOperation = hashSetOf<Monkey>()
        var pairToFind = "" to ""
        input.split("\n").forEach { monkeyLine ->
            val name = monkeyLine.take(4)
            if (name == human) {

            } else if (monkeyLine[6].isDigit()) {
                numberMonkeys[name] = monkeyLine.substring(6, monkeyLine.length).toLong()
            } else if (name == "root") {
                val first = monkeyLine.substring(6, 10)
                val second = monkeyLine.substring(13, monkeyLine.length)
                pairToFind = first to second
            } else {
                val first = monkeyLine.substring(6, 10)
                val second = monkeyLine.substring(13, monkeyLine.length)
                val op = monkeyLine[11].op()
                monkeysWithOperation.add(Monkey(name, first, second, op))
            }
        }
        val operationMap: Map<String, Monkey> = monkeysWithOperation.associateBy { it.name }
        while (monkeysWithOperation.isNotEmpty()) {
            val nextOperations = hashSetOf<Monkey>()
            monkeysWithOperation.forEach { monkey ->
                val firstNum = numberMonkeys[monkey.first]
                val secondNum = numberMonkeys[monkey.second]
                if (firstNum == null || secondNum == null) {
                    nextOperations.add(monkey)
                } else {
                    numberMonkeys[monkey.name] = monkey.operation.perform(firstNum, secondNum)
                }
            }
            if (monkeysWithOperation != nextOperations) {
                monkeysWithOperation = nextOperations
            } else {
                break
            }
        }
        var removed: String? = null
        val first = numberMonkeys[pairToFind.first]
        val second = numberMonkeys[pairToFind.second]
        var value: Long
        val find = if (first == null) {
            value = second!!
            pairToFind.first
        } else {
            value = first
            pairToFind.second
        }
        var opMonkey = operationMap[find]!!
        while (true) {
            numberMonkeys[opMonkey.first]?.apply {
                removed = opMonkey.first
            }
            numberMonkeys[opMonkey.second]?.apply {
                removed = opMonkey.second
            }
            if (removed != null) {
                val nextOperations = hashSetOf<Monkey>()
                monkeysWithOperation.forEach { monkey ->
                    val firstNum = numberMonkeys[monkey.first]
                    val secondNum = numberMonkeys[monkey.second]
                    if (firstNum == null || secondNum == null) {
                        nextOperations.add(monkey)
                    } else {
                        if (opMonkey.first == monkey.name || opMonkey.second == monkey.name) removed = monkey.name
                        numberMonkeys[monkey.name] = monkey.operation.perform(firstNum, secondNum)
                    }
                }
                monkeysWithOperation = nextOperations
            }
            if (removed != null) {
                val removedValue = numberMonkeys[removed]!!
                if (opMonkey.second == removed) {
                    value = opMonkey.operation.reverseFirst.perform(value, removedValue)
                    if (opMonkey.first == human) return value
                    opMonkey = operationMap[opMonkey.first]!!
                } else {
                    value = opMonkey.operation.reverseSecond.perform(value, removedValue)
                    if (opMonkey.second == human) return value
                    opMonkey = operationMap[opMonkey.second]!!
                }
            }
        }
    }

    private data class Monkey(val name: String, val first: String, val second: String, val operation: Operation)

    private sealed interface Operation {
        val reverseFirst: Operation
        val reverseSecond: Operation
        fun perform(val1: Long, val2: Long): Long

        object Plus : Operation {
            override val reverseFirst: Operation get() = Minus
            override val reverseSecond: Operation get() = Minus
            override fun perform(val1: Long, val2: Long): Long = val1 + val2
        }

        object Minus : Operation {
            override val reverseFirst: Operation get() = Plus
            override val reverseSecond: Operation get() = MinusReverse
            override fun perform(val1: Long, val2: Long): Long = val1 - val2
        }

        object MinusReverse : Operation {
            override val reverseFirst: Operation get() = Plus
            override val reverseSecond: Operation get() = Minus
            override fun perform(val1: Long, val2: Long): Long = val2 - val1
        }

        object Divide : Operation {
            override val reverseFirst: Operation get() = Multiply
            override val reverseSecond: Operation get() = DivideReverse
            override fun perform(val1: Long, val2: Long): Long = val1 / val2
        }

        object DivideReverse : Operation {
            override val reverseFirst: Operation get() = Multiply
            override val reverseSecond: Operation get() = Divide
            override fun perform(val1: Long, val2: Long): Long = val2 / val1
        }

        object Multiply : Operation {
            override val reverseFirst: Operation get() = Divide
            override val reverseSecond: Operation get() = Divide
            override fun perform(val1: Long, val2: Long): Long = val1 * val2
            override fun toString(): String = "multiply"
        }
    }
}
