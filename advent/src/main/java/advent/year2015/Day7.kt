package advent.year2015

object Day7 {
    fun toValueMap(input: String): Map<String, UInt> = buildMap {
        var lines = input.split("\n").map { it.trim() }.toSet()
        while (lines.isNotEmpty()) {
            val leftLines = hashSetOf<String>()
            lines.forEach { line ->
                val lastSpace = line.indexOfLast { char -> char == ' ' }
                val last = line.substring(lastSpace + 1, line.length)
                if (line.contains("NOT")) {
                    val before = line.indexOf(' ') + 1
                    val after = line.indexOf(' ', before)
                    val substring = line.substring(before, after)
                    val number = substring.toUIntOrNull() ?: get(substring)
                    if (number != null) {
                        put(last, number xor 65535u)
                    } else {
                        leftLines.add(line)
                    }
                } else if (line.contains("AND")) {
                    val firstPart = line.substring(0, line.indexOf(' '))
                    val firstNumber = firstPart.toUIntOrNull() ?: get(firstPart)
                    val secondPart = line.substring(line.indexOf("AND") + 4, line.indexOf('-') - 1)
                    val secondNumber = secondPart.toUIntOrNull() ?: get(secondPart)

                    if (firstNumber == null || secondNumber == null) {
                        leftLines.add(line)
                    } else {
                        put(last, firstNumber and secondNumber)
                    }
                } else if (line.contains("OR")) {
                    val firstPart = line.substring(0, line.indexOf(' '))
                    val firstNumber = firstPart.toUIntOrNull() ?: get(firstPart)
                    val secondPart = line.substring(line.indexOf("OR") + 3, line.indexOf('-') - 1)
                    val secondNumber = secondPart.toUIntOrNull() ?: get(secondPart)
                    if (firstNumber == null || secondNumber == null) {
                        leftLines.add(line)
                    } else {
                        put(last, firstNumber or secondNumber)
                    }
                } else if (line.contains("LSHIFT")) {
                    val firstPart = line.substring(0, line.indexOf(' '))
                    val firstNumber = firstPart.toUIntOrNull() ?: get(firstPart)
                    val secondPart = line.substring(line.indexOf("LSHIFT") + 7, line.indexOf('-') - 1)
                    val secondNumber = secondPart.toUIntOrNull() ?: get(secondPart)

                    if (firstNumber == null || secondNumber == null) {
                        leftLines.add(line)
                    } else {
                        put(last, firstNumber shl secondNumber.toInt())
                    }
                } else if (line.contains("RSHIFT")) {
                    val firstPart = line.substring(0, line.indexOf(' '))
                    val firstNumber = firstPart.toUIntOrNull() ?: get(firstPart)
                    val secondPart = line.substring(line.indexOf("RSHIFT") + 7, line.indexOf('-') - 1)
                    val secondNumber = secondPart.toUIntOrNull() ?: get(secondPart)

                    if (firstNumber == null || secondNumber == null) {
                        leftLines.add(line)
                    } else {
                        put(last, firstNumber shr secondNumber.toInt())
                    }
                } else {
                    val firstPart = line.substring(0, line.indexOf(' '))
                    val number = firstPart.toUIntOrNull() ?: get(firstPart)
                    if (number != null) {
                        put(last, number)
                    } else {
                        leftLines.add(line)
                    }
                }
            }
            lines = leftLines
        }
    }
}
