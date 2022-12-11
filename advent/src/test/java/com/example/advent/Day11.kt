package com.example.advent

import org.junit.Test

class Day11 {
    @Test
    fun sample() {
        val sampleInput = """
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
    """.trim().part1()
    }

    @Test
    fun actual() {
        """Monkey 0:
  Starting items: 54, 53
  Operation: new = old * 3
  Test: divisible by 2
    If true: throw to monkey 2
    If false: throw to monkey 6

Monkey 1:
  Starting items: 95, 88, 75, 81, 91, 67, 65, 84
  Operation: new = old * 11
  Test: divisible by 7
    If true: throw to monkey 3
    If false: throw to monkey 4

Monkey 2:
  Starting items: 76, 81, 50, 93, 96, 81, 83
  Operation: new = old + 6
  Test: divisible by 3
    If true: throw to monkey 5
    If false: throw to monkey 1

Monkey 3:
  Starting items: 83, 85, 85, 63
  Operation: new = old + 4
  Test: divisible by 11
    If true: throw to monkey 7
    If false: throw to monkey 4

Monkey 4:
  Starting items: 85, 52, 64
  Operation: new = old + 8
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 7

Monkey 5:
  Starting items: 57
  Operation: new = old + 2
  Test: divisible by 5
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 6:
  Starting items: 60, 95, 76, 66, 91
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 2
    If false: throw to monkey 5

Monkey 7:
  Starting items: 65, 84, 76, 72, 79, 65
  Operation: new = old + 5
  Test: divisible by 19
    If true: throw to monkey 6
    If false: throw to monkey 0
""".part1()
    }

    private fun String.part1() {
        println("part 1: ")
        monkeyCycle(20) { it / 3 }
        println("part 2: ")
        monkeyCycle(10_000) { it }
    }

    private fun String.monkeyCycle(rounds: Int, releafe: (Long) -> Long) {
        val monkeys = mutableListOf<Monkey>()
        split("\n\n").forEach { monkey ->
            monkeys.add(monkey.toMonkey())
        }
        var modulus = 1L
        monkeys.forEach {
            modulus *= it.check.divider
        }
        repeat(rounds) { counter ->
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeFirst()
                    monkey.bump()
                    val newValue = releafe(monkey.operation.update(item)) % modulus
                    val addTo = monkey.check.next(newValue)
                    monkeys[addTo].items.add(newValue)
                }
            }
            if (counter + 1 == 20 || (counter + 1) % 1000 == 0) {
                println(counter + 1)
                printMonkeys(monkeys)
            }
        }
//        printMonkeys(monkeys)
        monkeys.sortBy { it.opCount }
        val (one, two) = monkeys.takeLast(2)
        println("${one.opCount}  ${two.opCount}  ${one.opCount * two.opCount}")
    }

    private fun printMonkeys(monkeys: MutableList<Monkey>) {
        monkeys.forEachIndexed { index, monkey ->
            println("monkey $index monkey ${monkey.opCount}")
        }
    }

    private fun String.toMonkey(): Monkey {
        val lines = split("\n")
        val items = lines[1].trim().run { substring(16, length).split(",").map { it.trim().toLong() } }.toMutableList()
        val operation = lines[2].trim().run {
            val opChar = get(21)
            if (opChar == '+') {
                Operation.Add(substring(23, length).toLong())
            } else if (opChar == '*') {
                val end = substring(23, length)
                if (end == "old") {
                    Operation.Square
                } else {
                    Operation.Multiply(end.toLong())
                }
            } else {
                throw RuntimeException("bad operation")
            }
        }
        val divider = lines[3].trim().run { substring(19, length).toInt() }
        val positive = lines[4].trim().run { substring(25, length).toInt() }
        val negative = lines[5].trim().run { substring(26, length).toInt() }
        return Monkey(items, operation, Check(divider, positive, negative))
    }

    data class Monkey(val items: MutableList<Long>, val operation: Operation, val check: Check) {
        var opCount = 0L
        fun bump() {
            opCount += 1
        }
    }

    sealed interface Operation {
        fun update(old: Long): Long

        data class Add(val value: Long) : Operation {
            override fun update(old: Long): Long = old + value
        }

        data class Multiply(val value: Long) : Operation {
            override fun update(old: Long): Long = old * value
        }

        object Square : Operation {
            override fun update(old: Long): Long = old * old
        }
    }

    data class Check(val divider: Int, val positive: Int, val negative: Int) {
        fun next(value: Long): Int = if (value % divider == 0L) positive else negative
    }
}
