package advent.year2023

object Day5 {
    private data class MapRange(val destinationStart: Long, val sourceStart: Long, val count: Long) {
        val offset = destinationStart - sourceStart
        val sourceEnd = sourceStart + count - 1
        val destinationEnd = destinationStart + count - 1
    }

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

    private fun convertToMaps(lines: List<String>): List<List<MapRange>> = buildList {
        var pairList = mutableListOf<MapRange>()
        for (line in lines) {
            if (line.isBlank()) {
                if (pairList.isNotEmpty()) {
                    add(pairList)
                    pairList = mutableListOf()
                }
            } else if (line[0].isDigit()) {
                val numbers = line.split(' ').map { it.toLong() }
                val element = MapRange(numbers[0], numbers[1], numbers[2])
                pairList.add(element)
            }
        }
        if (pairList.isNotEmpty()) {
            add(pairList)
        }
    }

    private fun convertWhilePossible(conversionsList: List<List<MapRange>>, index: Int, source: Long): Long {
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
        parsedRanges: List<List<MapRange>>,
        ranges: List<Pair<Long, Long>>
    ): Long {
        var destinationRanges = ranges
        for (rangeList in parsedRanges) {
            destinationRanges = updateRanges(rangeList, destinationRanges)
        }
        return destinationRanges.minOf { it.first }
    }

    private fun updateRanges(
        rangeList: List<MapRange>,
        sourceRanges: List<Pair<Long, Long>>
    ): List<Pair<Long, Long>> {
        val destinationRanges = mutableSetOf<Pair<Long, Long>>()

        for (range in sourceRanges) {
            var rangesToIterate = hashSetOf(range)
            for (map in rangeList) {
                val leftOverRange = hashSetOf<Pair<Long, Long>>()
                rangesToIterate.forEach { (inputStart, inputEnd) ->
                    if (inputEnd < map.sourceStart || inputStart > map.sourceEnd) {
                        leftOverRange.add(inputStart to inputEnd)
                    } else if (inputStart >= map.sourceStart && inputEnd <= map.sourceEnd) {
                        destinationRanges.add(
                            inputStart + map.offset to inputEnd + map.offset
                        )
                    } else {
                        val start = if (inputStart < map.sourceStart) {
                            leftOverRange.add(inputStart to map.sourceStart - 1)
                            map.destinationStart
                        } else {
                            inputStart + map.offset
                        }
                        val end = if (inputEnd <= map.sourceEnd) {
                            inputEnd + map.offset
                        } else {
                            leftOverRange.add(map.sourceEnd + 1 to inputEnd)
                            map.destinationEnd
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

    private fun convert(maps: List<MapRange>, source: Long): Long {
        for (map in maps) {
            if (source >= map.sourceStart && source <= map.sourceEnd) return source + map.offset
        }
        return source
    }
}
