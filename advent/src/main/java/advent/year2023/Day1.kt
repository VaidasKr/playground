package advent.year2023

object Day1 {
    fun part1(input: String): Long {
        return input.trim().split("\n").sumOf { line ->
            val numbers = line.filter { it.isDigit() }.map { it.digitToInt() }
            (numbers.first() * 10 + numbers.last()).toLong()
        }
    }

    private val numbers = listOf(
        "---",
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
        "---",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9"
    )

    fun part2(input: String): Long {
        return input.trim().split("\n").sumOf { line ->
            var firstNumberIndex = Int.MAX_VALUE
            var lastNumberIndex = -1
            var firstNumber = -1
            var lastNumber = -1

            for (i in numbers.indices) {
                val number = numbers[i]
                var index = line.indexOf(number)
                while (index != -1) {
                    if (index < firstNumberIndex) {
                        firstNumberIndex = index
                        firstNumber = i % 10
                    }
                    if (index > lastNumberIndex) {
                        lastNumberIndex = index
                        lastNumber = i % 10
                    }
                    index = line.indexOf(number, index + 1)
                }
            }
            if (firstNumber == -1 || lastNumber == -1) throw RuntimeException("failed to parse $line")
            firstNumber.toLong() * 10 + lastNumber
        }
    }
}
