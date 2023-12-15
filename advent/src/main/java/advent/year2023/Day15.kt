package advent.year2023

object Day15 {
    fun sumOfAsciiHash(input: String): Long {
        return input.split(',').sumOf { part ->
            var sum = 0L
            for (c in part) {
                sum += c.code
                sum *= 17
                sum %= 256
            }
            sum
        }
    }

    fun sumOfFocusPower(input: String): Long {
        val boxes = HashMap<Int, ArrayList<String>>()
        val focalMap = HashMap<String, Int>()
        input.split(',').forEach { part ->
            var sum = 0
            val size = part.indexOfAny(charArrayOf('-', '='))
            val label = part.take(size)
            for (c in label) {
                sum += c.code
                sum *= 17
                sum %= 256
            }
            val labels = boxes.getOrPut(sum) { ArrayList() }
            when (part[size]) {
                '-' -> labels.remove(label)
                '=' -> {
                    focalMap[label] = part[size + 1].digitToInt()
                    if (!labels.contains(label)) labels.add(label)
                }
            }
        }
        var sum = 0L
        boxes.forEach { (box, labels) ->
            for (i in labels.indices) {
                sum += (box + 1) * (i + 1) * focalMap[labels[i]]!!
            }
        }
        return sum
    }
}
