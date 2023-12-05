package advent.year2023

object Day5 {
    fun findLowestLocationNumber(input: String): Long {
        var min = Long.MAX_VALUE
        val lines = input.split('\n')

        val nextLines = lines.drop(2)

        val parsedRanges = convertToMaps(nextLines)

        lines[0].substring(lines[0].indexOf(':') + 2).split(' ').map { it.toLong() }
            .forEach { seed ->
                val locationForSeed = convertWhilePossible(parsedRanges, 0, seed)
                if (locationForSeed < min) {
                    min = locationForSeed
                }
            }
        return min
    }

    private fun convertWhilePossible(
        conversionsList: List<List<Triple<Long, Long, Long>>>, index: Int, source: Long
    ): Long {
        if (index == conversionsList.size) return source
        val conversions = conversionsList[index]
        val destination = convert(conversions, source)
        return convertWhilePossible(conversionsList, index + 1, destination)
    }

    fun findLowestLocationNumberWithRanges(input: String): Long {
        val lines = input.split('\n')
        val numbers = lines[0].substring(lines[0].indexOf(':') + 2).split(' ').map { it.toLong() }

        val nextLines = lines.drop(2)

        val parsedRanges = convertToMaps(nextLines)

        val ranges = buildList {
            for (i in numbers.indices step 2) {
                val rangeStart = numbers[i]
                val rangeSize = numbers[i + 1]
                add(rangeStart to rangeStart + rangeSize - 1)
            }
        }

        return findLowestDestinationForRanges(parsedRanges, ranges)
    }

    private fun findLowestDestinationForRanges(
        parsedRanges: List<List<Triple<Long, Long, Long>>>,
        ranges: List<Pair<Long, Long>>
    ): Long {
        var destinationRanges = ranges
        for (rangeList in parsedRanges) {
            destinationRanges = updateRanges(rangeList, destinationRanges)
        }
        var min = destinationRanges.first().first
        destinationRanges.forEach { range ->
            if (range.first < min) {
                min = range.first
            }
        }
        return min
    }

    private fun updateRanges(
        rangeList: List<Triple<Long, Long, Long>>,
        sourceRanges: List<Pair<Long, Long>>
    ): List<Pair<Long, Long>> {
        val destinationRanges = mutableSetOf<Pair<Long, Long>>()

        for (range in sourceRanges) {
            var rangesToIterate = hashSetOf(range)
            for (map in rangeList) {
                val leftOverRange = hashSetOf<Pair<Long, Long>>()
                val destinationStart = map.first
                val destinationEnd = destinationStart + map.third - 1
                val sourceStart = map.second
                val sourceEnd = sourceStart + map.third - 1
                rangesToIterate.forEach { inputRange ->
                    val inputStart = inputRange.first
                    val inputEnd = inputRange.second
                    if (inputEnd < sourceStart || inputStart > sourceEnd) {
                        leftOverRange.add(inputRange)
                    } else if (inputStart >= sourceStart && inputEnd <= sourceEnd) {
                        destinationRanges.add(
                            inputStart - sourceStart + destinationStart to inputEnd - sourceStart + destinationStart
                        )
                    } else {
                        val start = if (inputStart < sourceStart) {
                            leftOverRange.add(inputStart to sourceStart - 1)
                            destinationStart
                        } else {
                            inputStart - sourceStart + destinationStart
                        }
                        val end = if (inputEnd <= sourceEnd) {
                            inputEnd - sourceStart + destinationStart
                        } else {
                            leftOverRange.add(sourceEnd + 1 to inputEnd)
                            destinationEnd
                        }
                        destinationRanges.add(start to end)
                    }
                }
                rangesToIterate = leftOverRange
            }
            destinationRanges.addAll(rangesToIterate)
        }

        return destinationRanges.toList()
    }

    private fun convert(conversions: List<Triple<Long, Long, Long>>, source: Long): Long {
        for (conversion in conversions) {
            if (source >= conversion.second && source < conversion.second + conversion.third) {
                return source - conversion.second + conversion.first
            }
        }
        return source
    }

    private fun convertToMaps(lines: List<String>): List<List<Triple<Long, Long, Long>>> {
        return buildList {
            var pairList = mutableListOf<Triple<Long, Long, Long>>()
            for (line in lines) {
                if (line.isBlank()) {
                    if (pairList.isNotEmpty()) {
                        add(pairList)
                        pairList = mutableListOf()
                    }
                } else if (line[0].isDigit()) {
                    val numbers = line.split(' ').map { it.toLong() }
                    val element = Triple(numbers[0], numbers[1], numbers[2])
                    pairList.add(element)
                }
            }
            if (pairList.isNotEmpty()) {
                add(pairList)
            }
        }
    }

    fun findLowestLocationNumberWithRangesOld(input: String): Long {
        var min = Long.MAX_VALUE
        val lines = input.split('\n')
        val numbers = lines[0].substring(lines[0].indexOf(':') + 2).split(' ').map { it.toLong() }

        val nextLines = lines.drop(2)

        val parsedRanges = convertToMaps(nextLines)

        for (i in numbers.indices step 2) {
            val rangeStart = numbers[i]
            val rangeEnd = numbers[i + 1]
            repeat(rangeEnd.toInt()) {
                val locationForSeed = convertWhilePossible(parsedRanges, 0, rangeStart + it)
                if (locationForSeed < min) {
                    min = locationForSeed
                }
            }
        }
        return min
    }
}
