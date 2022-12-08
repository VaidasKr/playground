package lt.vaikri.playground

import org.junit.Test

class Day5 {
    @Test
    fun sample() {
        """
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
        """.trimIndent()
            .printResults()
    }

    @Test
    fun actual() {
        """
    [W]         [J]     [J]        
    [V]     [F] [F] [S] [S]        
    [S] [M] [R] [W] [M] [C]        
    [M] [G] [W] [S] [F] [G]     [C]
[W] [P] [S] [M] [H] [N] [F]     [L]
[R] [H] [T] [D] [L] [D] [D] [B] [W]
[T] [C] [L] [H] [Q] [J] [B] [T] [N]
[G] [G] [C] [J] [P] [P] [Z] [R] [H]
 1   2   3   4   5   6   7   8   9 

move 3 from 4 to 3
move 3 from 8 to 6
move 2 from 3 to 8
move 3 from 7 to 2
move 1 from 1 to 3
move 6 from 2 to 7
move 5 from 3 to 6
move 1 from 8 to 6
move 4 from 4 to 3
move 2 from 1 to 2
move 10 from 7 to 3
move 1 from 7 to 2
move 6 from 5 to 8
move 1 from 1 to 4
move 7 from 6 to 3
move 22 from 3 to 4
move 3 from 2 to 8
move 4 from 6 to 8
move 5 from 2 to 1
move 3 from 9 to 4
move 2 from 4 to 3
move 1 from 9 to 2
move 1 from 5 to 3
move 1 from 2 to 6
move 1 from 5 to 2
move 1 from 2 to 7
move 4 from 4 to 5
move 2 from 1 to 9
move 1 from 1 to 3
move 2 from 5 to 9
move 5 from 9 to 8
move 1 from 5 to 9
move 1 from 7 to 2
move 1 from 9 to 4
move 5 from 6 to 7
move 1 from 5 to 2
move 2 from 2 to 4
move 2 from 7 to 4
move 2 from 7 to 8
move 21 from 8 to 6
move 6 from 3 to 1
move 1 from 7 to 9
move 1 from 1 to 7
move 7 from 6 to 8
move 3 from 1 to 9
move 24 from 4 to 8
move 3 from 1 to 3
move 10 from 6 to 8
move 1 from 4 to 5
move 1 from 3 to 9
move 5 from 9 to 8
move 11 from 8 to 3
move 1 from 5 to 7
move 1 from 1 to 8
move 1 from 6 to 1
move 19 from 8 to 1
move 1 from 7 to 9
move 10 from 3 to 1
move 3 from 3 to 8
move 1 from 7 to 3
move 1 from 9 to 2
move 23 from 1 to 7
move 1 from 1 to 9
move 1 from 3 to 6
move 2 from 6 to 9
move 7 from 8 to 1
move 8 from 8 to 1
move 11 from 7 to 2
move 2 from 6 to 8
move 1 from 6 to 8
move 7 from 8 to 6
move 1 from 9 to 4
move 1 from 8 to 1
move 10 from 7 to 1
move 6 from 2 to 5
move 5 from 2 to 9
move 4 from 5 to 8
move 1 from 5 to 8
move 13 from 1 to 6
move 1 from 2 to 4
move 1 from 4 to 5
move 2 from 9 to 4
move 3 from 9 to 4
move 2 from 5 to 3
move 1 from 3 to 9
move 2 from 8 to 5
move 2 from 5 to 7
move 2 from 8 to 6
move 2 from 7 to 3
move 2 from 7 to 8
move 4 from 1 to 3
move 3 from 8 to 4
move 8 from 4 to 9
move 1 from 9 to 8
move 3 from 3 to 6
move 4 from 3 to 9
move 1 from 8 to 2
move 12 from 1 to 5
move 9 from 6 to 8
move 1 from 4 to 8
move 3 from 1 to 3
move 12 from 5 to 8
move 1 from 2 to 6
move 1 from 3 to 1
move 1 from 3 to 2
move 1 from 1 to 2
move 16 from 6 to 1
move 1 from 6 to 3
move 2 from 3 to 8
move 7 from 8 to 5
move 1 from 2 to 6
move 1 from 2 to 1
move 2 from 9 to 4
move 1 from 6 to 7
move 8 from 9 to 8
move 5 from 5 to 6
move 9 from 8 to 7
move 12 from 1 to 3
move 2 from 6 to 3
move 6 from 8 to 9
move 5 from 1 to 4
move 2 from 5 to 7
move 11 from 7 to 3
move 1 from 7 to 4
move 2 from 6 to 8
move 7 from 4 to 6
move 3 from 8 to 7
move 3 from 8 to 2
move 19 from 3 to 2
move 4 from 8 to 7
move 2 from 9 to 8
move 1 from 4 to 5
move 1 from 6 to 8
move 1 from 5 to 7
move 8 from 9 to 4
move 1 from 8 to 5
move 1 from 5 to 6
move 4 from 2 to 7
move 8 from 6 to 9
move 6 from 7 to 3
move 4 from 3 to 8
move 5 from 8 to 7
move 15 from 2 to 8
move 8 from 3 to 4
move 7 from 9 to 7
move 3 from 2 to 4
move 2 from 7 to 4
move 2 from 4 to 3
move 1 from 9 to 4
move 9 from 7 to 5
move 4 from 5 to 9
move 2 from 5 to 3
move 2 from 9 to 1
move 3 from 5 to 2
move 4 from 3 to 1
move 7 from 7 to 4
move 3 from 2 to 6
move 4 from 4 to 5
move 2 from 1 to 6
move 8 from 4 to 1
move 1 from 8 to 2
move 1 from 2 to 8
move 11 from 8 to 7
move 3 from 5 to 9
move 1 from 5 to 9
move 11 from 7 to 1
move 7 from 8 to 9
move 11 from 1 to 3
move 6 from 4 to 5
move 8 from 1 to 7
move 4 from 6 to 5
move 3 from 5 to 8
move 8 from 7 to 3
move 7 from 4 to 7
move 7 from 5 to 6
move 3 from 3 to 8
move 2 from 4 to 9
move 16 from 3 to 1
move 7 from 7 to 1
move 2 from 8 to 7
move 2 from 8 to 1
move 1 from 8 to 4
move 1 from 7 to 4
move 2 from 4 to 2
move 1 from 8 to 7
move 1 from 2 to 3
move 1 from 2 to 4
move 1 from 7 to 8
move 8 from 6 to 7
move 1 from 3 to 5
move 15 from 1 to 2
move 4 from 9 to 1
move 1 from 8 to 1
move 11 from 9 to 2
move 21 from 2 to 6
move 1 from 4 to 2
move 4 from 2 to 7
move 1 from 5 to 9
move 1 from 9 to 4
move 19 from 1 to 2
move 5 from 2 to 4
move 8 from 7 to 6
move 10 from 6 to 2
move 5 from 7 to 5
move 2 from 4 to 1
move 3 from 6 to 9
move 3 from 9 to 2
move 1 from 5 to 2
move 13 from 6 to 3
move 2 from 6 to 9
move 17 from 2 to 3
move 1 from 6 to 2
move 2 from 2 to 1
move 2 from 1 to 5
move 5 from 5 to 3
move 2 from 2 to 8
move 10 from 2 to 1
move 18 from 3 to 8
move 13 from 8 to 1
move 7 from 8 to 2
move 2 from 2 to 1
move 4 from 3 to 8
move 1 from 2 to 7
move 1 from 2 to 8
move 2 from 4 to 1
move 1 from 5 to 4
move 1 from 9 to 6
move 1 from 1 to 7
move 11 from 3 to 4
move 1 from 6 to 2
move 7 from 1 to 2
move 5 from 8 to 5
move 1 from 7 to 5
move 3 from 5 to 1
move 7 from 1 to 6
move 6 from 1 to 6
move 6 from 1 to 8
move 2 from 1 to 3
move 5 from 2 to 5
move 1 from 7 to 6
move 1 from 4 to 2
move 4 from 2 to 4
move 1 from 1 to 9
move 1 from 3 to 8
move 7 from 8 to 5
move 1 from 9 to 7
move 1 from 9 to 4
move 8 from 5 to 7
move 5 from 4 to 1
move 4 from 1 to 6
move 3 from 1 to 6
move 3 from 3 to 6
move 1 from 5 to 6
move 3 from 7 to 5
move 15 from 6 to 7
move 12 from 7 to 4
move 8 from 5 to 2
move 3 from 4 to 9
move 3 from 9 to 7
move 1 from 6 to 2
move 9 from 4 to 9
move 4 from 9 to 1
move 2 from 1 to 7
move 3 from 6 to 4
move 3 from 6 to 4
move 2 from 1 to 2
move 1 from 5 to 6
move 2 from 9 to 4
move 13 from 4 to 2
move 22 from 2 to 3
move 3 from 7 to 8
move 1 from 9 to 6
move 1 from 9 to 3
move 2 from 8 to 9
move 3 from 9 to 8
move 5 from 6 to 4
move 2 from 8 to 6
move 4 from 7 to 8
move 2 from 2 to 5
move 4 from 8 to 7
move 2 from 5 to 7
move 7 from 7 to 2
move 9 from 4 to 7
move 4 from 2 to 1
move 3 from 7 to 6
move 12 from 3 to 5
move 5 from 2 to 5
move 1 from 8 to 2
move 1 from 3 to 5
move 4 from 3 to 1
move 2 from 6 to 1
move 11 from 5 to 3
move 3 from 6 to 1
move 8 from 1 to 9
move 5 from 9 to 8
move 2 from 9 to 7
move 1 from 1 to 8
move 4 from 7 to 6
move 6 from 3 to 1
move 1 from 9 to 7
move 5 from 7 to 4
move 3 from 8 to 3
move 1 from 6 to 5
move 2 from 2 to 1
move 4 from 7 to 9
move 3 from 8 to 6
move 6 from 3 to 8
move 6 from 8 to 7
move 4 from 6 to 5
move 6 from 5 to 8
move 2 from 9 to 5
move 2 from 9 to 8
move 4 from 7 to 4
move 1 from 6 to 3
move 5 from 8 to 4
move 1 from 6 to 9
move 1 from 7 to 3
move 7 from 3 to 8
move 6 from 1 to 4
move 6 from 1 to 2
move 17 from 4 to 6
move 4 from 8 to 5
move 3 from 3 to 1
move 5 from 4 to 1
move 5 from 2 to 7
move 7 from 8 to 1
move 7 from 7 to 2
move 4 from 6 to 3
move 6 from 1 to 8
move 2 from 4 to 9
move 2 from 5 to 4
move 1 from 4 to 3
move 1 from 4 to 7
move 2 from 7 to 5
move 4 from 5 to 3
move 1 from 9 to 1
move 5 from 5 to 3
move 1 from 8 to 5
move 7 from 6 to 1
move 6 from 6 to 8
move 11 from 3 to 7
move 2 from 9 to 1
move 8 from 8 to 2
move 5 from 7 to 5
move 5 from 7 to 4
move 1 from 2 to 6
move 2 from 4 to 6
move 1 from 7 to 5
move 2 from 6 to 4
move 10 from 2 to 6
move 3 from 4 to 5
move 1 from 6 to 4
move 4 from 6 to 4
move 6 from 6 to 9
move 3 from 3 to 8
move 19 from 1 to 8
move 23 from 8 to 9
move 1 from 8 to 1
move 1 from 1 to 7
move 1 from 7 to 1
move 1 from 1 to 6
move 5 from 9 to 5
move 1 from 8 to 5
move 5 from 4 to 5
move 4 from 5 to 4
move 1 from 9 to 1
move 6 from 9 to 3
move 2 from 2 to 8
move 1 from 1 to 3
move 1 from 6 to 7
move 1 from 7 to 3
move 1 from 2 to 5
move 6 from 9 to 8
move 5 from 4 to 5
move 10 from 5 to 2
move 10 from 5 to 2
move 11 from 9 to 1
move 4 from 2 to 6
move 18 from 2 to 9
move 2 from 6 to 9
move 3 from 3 to 9
move 1 from 4 to 3
move 1 from 6 to 8
move 6 from 8 to 4
move 6 from 5 to 7
move 19 from 9 to 4
move 7 from 1 to 3
move 1 from 6 to 8
move 4 from 8 to 7
move 2 from 3 to 6
move 3 from 1 to 8
move 1 from 1 to 5
move 7 from 7 to 3
move 8 from 3 to 1
move 1 from 5 to 7
move 2 from 6 to 2
move 3 from 1 to 8
move 1 from 2 to 6
move 3 from 1 to 7
move 4 from 8 to 9
move 4 from 7 to 6
move 3 from 9 to 7
move 3 from 9 to 3
move 6 from 7 to 3
move 13 from 3 to 1
move 5 from 3 to 4
move 1 from 8 to 7
move 1 from 7 to 9
move 1 from 8 to 5
move 1 from 9 to 4
move 1 from 5 to 2
move 2 from 9 to 2
move 3 from 6 to 2
move 1 from 3 to 7
move 13 from 4 to 8
move 14 from 1 to 5
move 6 from 2 to 7
move 4 from 8 to 7
move 1 from 1 to 3
move 1 from 2 to 6
move 5 from 4 to 2
move 4 from 8 to 4
move 12 from 5 to 4
move 1 from 3 to 8
move 9 from 4 to 2
move 9 from 4 to 5
move 1 from 4 to 5
move 6 from 4 to 3
move 5 from 8 to 4
move 9 from 4 to 7
move 4 from 2 to 3
move 8 from 7 to 1
move 2 from 7 to 1
move 2 from 2 to 9
move 1 from 6 to 7
move 2 from 6 to 3
move 1 from 2 to 3
move 2 from 7 to 3
move 3 from 3 to 7
move 8 from 1 to 2
move 9 from 5 to 3
move 15 from 2 to 7
move 20 from 7 to 5
move 23 from 5 to 6
move 20 from 6 to 8
move 1 from 6 to 4
move 2 from 9 to 7
move 1 from 4 to 6
move 3 from 7 to 6
move 2 from 7 to 5
move 13 from 3 to 5
move 3 from 7 to 1
move 13 from 5 to 4
move 3 from 1 to 4
move 5 from 6 to 1
move 6 from 4 to 3
move 1 from 7 to 4
move 11 from 8 to 6
move 1 from 8 to 6
move 2 from 1 to 5
move 2 from 5 to 3
move 11 from 6 to 5
move 3 from 8 to 3
move 4 from 3 to 5
move 15 from 5 to 1
move 1 from 3 to 5
move 3 from 8 to 5
move 1 from 5 to 9
move 1 from 5 to 3
move 9 from 4 to 6
move 7 from 6 to 8
move 2 from 4 to 6
move 2 from 5 to 1
move 8 from 8 to 7
move 6 from 6 to 2
move 1 from 5 to 2
move 4 from 3 to 4
move 6 from 1 to 5
move 7 from 3 to 4
move 2 from 3 to 2
move 2 from 8 to 9
move 9 from 2 to 5
move 9 from 5 to 4
move 2 from 3 to 6
move 14 from 1 to 7
move 15 from 7 to 2
move 1 from 1 to 7
move 7 from 5 to 1
move 2 from 9 to 2
move 2 from 1 to 7
move 1 from 1 to 4
move 2 from 6 to 8
move 7 from 2 to 8
move 1 from 9 to 6
move 7 from 8 to 3
move 1 from 6 to 4
move 1 from 8 to 2
move 6 from 4 to 6
move 9 from 2 to 1
move 1 from 3 to 9
move 3 from 7 to 5
        """.trimIndent()
            .printResults()
    }

    private fun String.printResults() {
        val splitted = split("move")
        val boxes = splitted.first().trimEnd()
        val boxLines = boxes.split("\n")
        parseAndMove(boxLines, splitted) { command, boxMap -> takeBy1(command, boxMap) }
        println()
        parseAndMove(boxLines, splitted) { command, boxMap -> takeMultiple(command, boxMap) }
    }

    private fun parseAndMove(
        boxLines: List<String>,
        splitted: List<String>,
        command: (String, MutableMap<String, MutableList<Char>>) -> Unit
    ) {
        val boxMap = mutableMapOf<String, MutableList<Char>>()
        boxLines.reversed().drop(1).forEach { line ->
            val startOffset = 1
            var position = 1
            var offset = startOffset
            while (offset < line.length) {
                val charAtPosition = line[offset]
                if (charAtPosition.isLetter()) {
                    boxMap.getOrPut(position.toString()) { mutableListOf() }.add(charAtPosition)
                }
                position++
                offset += 4
            }
        }

        val commands = splitted.drop(1)
        commands.forEach { command(it, boxMap) }
        boxMap.forEach { (_, values) ->
            if (values.isNotEmpty()) {
                print(values.last())
            }
        }
    }

    private fun takeBy1(command: String, boxMap: MutableMap<String, MutableList<Char>>) {
        val words = command.trim().split(" ")
        val amount = words.first().toInt()
        val from = boxMap[words[2]]!!
        val to = boxMap[words[4]]!!
        for (i in 0 until amount) {
            to.add(from.take())
        }
    }

    private fun MutableList<Char>.take(): Char = removeAt(lastIndex)

    private fun MutableList<Char>.takeFromBack(n: Int): List<Char> {
        val takeLast = takeLast(n)
        for (i in 0 until n) {
            removeLast()
        }
        return takeLast
    }

    private fun takeMultiple(command: String, boxMap: MutableMap<String, MutableList<Char>>) {
        val words = command.trim().split(" ")
        val amount = words.first().toInt()
        val from = boxMap[words[2]]!!
        val to = boxMap[words[4]]!!
        to.addAll(from.takeFromBack(amount))
    }
}
