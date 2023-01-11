package advent.year2015

class Day16(input: String) {
    private val propertyNames = arrayOf(
        "children",
        "cats",
        "samoyeds",
        "pomeranians",
        "akitas",
        "vizslas",
        "goldfish",
        "trees",
        "cars",
        "perfumes"
    )
    private val operations = arrayOf(0, 1, 0, -1, 0, 0, -1, 1, 0, 0)
    private val properties: IntArray

    init {
        val lines = input.lines()
        val properties = IntArray(lines.size * 10) { -1 }
        for (i in lines.indices) {
            val line = lines[i].trim()
            val propertyString = line.substring(line.indexOf(':') + 2, line.length)
            propertyString.split(", ").forEach { property ->
                for (x in propertyNames.indices) {
                    val propertyName = propertyNames[x]
                    if (property.startsWith(propertyName)) {
                        properties[i * propertyNames.size + x] = property.numberAfter(propertyName)
                    }
                }
            }
        }
        this.properties = properties
    }

    private fun String.numberAfter(name: String): Int = substring(name.length + 2, length).toInt()

    fun findMatchingSueIndex(
        children: Int,
        cats: Int,
        samoyeds: Int,
        pomeranians: Int,
        akitas: Int,
        vizslas: Int,
        goldfish: Int,
        trees: Int,
        cars: Int,
        perfumes: Int
    ): Int {
        val searchProperties =
            intArrayOf(children, cats, samoyeds, pomeranians, akitas, vizslas, goldfish, trees, cars, perfumes)
        val size = propertyNames.size
        out@ for (i in 0 until properties.size / size) {
            for (j in searchProperties.indices) {
                val value = searchProperties[j]
                val aunt = properties[i * size + j]
                if (aunt != -1 && aunt != value) {
                    continue@out
                }
            }
            return i
        }
        return -1
    }

    fun findMatchingSueIndexAdjusted(
        children: Int,
        cats: Int,
        samoyeds: Int,
        pomeranians: Int,
        akitas: Int,
        vizslas: Int,
        goldfish: Int,
        trees: Int,
        cars: Int,
        perfumes: Int
    ): Int {
        val searchProperties =
            intArrayOf(children, cats, samoyeds, pomeranians, akitas, vizslas, goldfish, trees, cars, perfumes)
        val size = propertyNames.size
        out@ for (i in 0 until properties.size / size) {
            for (j in searchProperties.indices) {
                val value = searchProperties[j]
                val aunt = properties[i * size + j]
                if (aunt != -1) {
                    when (operations[j]) {
                        0 -> {
                            if (aunt != value) {
                                continue@out
                            }
                        }
                        1 -> {
                            if (aunt <= value) {
                                continue@out
                            }
                        }
                        -1 -> {
                            if (aunt >= value) {
                                continue@out
                            }
                        }
                    }
                }
            }
            return i
        }
        return -1
    }
}
