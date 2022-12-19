package advent.year2022

import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.ceil
import kotlin.math.min

class Day19(input: String) {
    private val blueprints: List<Blueprint>

    init {
        val priceArray = IntArray(7)
        blueprints = input.split('\n').map { line ->
            var numIndex = 0
            var number = 0
            line.forEach { char ->
                if (char.isDigit()) {
                    number = number * 10 + char.digitToInt()
                } else if (number != 0) {
                    priceArray[numIndex++] = number
                    number = 0
                }
            }
            Blueprint(
                id = priceArray[0],
                oreOrePrice = priceArray[1],
                clayOrePrice = priceArray[2],
                obsidianOrePrice = priceArray[3],
                obsidianClayPrice = priceArray[4],
                geodeOrePrice = priceArray[5],
                geodeObsidianPrice = priceArray[6]
            )
        }
    }

    fun qualitySums(time: Int): Sequence<Int> = sequence {
        for (print in blueprints) yield(print.search(time))
    }

    fun qualityLevelSum(time: Int): Int = blueprints.sumOf { blueprint -> blueprint.id * blueprint.search(time) }

    private data class Blueprint(
        val id: Int,
        val oreOrePrice: Int,
        val clayOrePrice: Int,
        val obsidianOrePrice: Int,
        val obsidianClayPrice: Int,
        val geodeOrePrice: Int,
        val geodeObsidianPrice: Int
    ) {
        fun search(time: Int): Int {
            val startState = CollectState(
                minute = 0,
                ore = 0,
                clay = 0,
                obsidian = 0,
                geode = 0,
                oreProd = 1,
                clayProd = 0,
                obsidianProd = 0,
                geodeProd = 0
            )
            return searchOres(time, startState, AtomicInteger(0))
        }

        private fun searchOres(time: Int, state: CollectState, highest: AtomicInteger): Int {
            if (state.minute >= time) {
                highest.updateAndGet { state.geode.coerceAtLeast(it) }
                return state.geode
            }
            val minuteLeft = time - state.minute
            val maxCanProduce = state.geode + (0 until minuteLeft).sumOf { state.geodeProd + it }
            if (maxCanProduce < highest.get()) return 0

            var max = 0

            if (state.obsidianProd > 0) {
                val oresNeeded = geodeOrePrice - state.ore
                val obsNeeded = geodeObsidianPrice - state.obsidian
                val minToWait = if (oresNeeded <= 0 && obsNeeded <= 0) {
                    1
                } else {
                    ceil(oresNeeded / state.oreProd.toFloat()).toInt()
                        .coerceAtLeast(ceil(obsNeeded / state.obsidianProd.toFloat()).toInt()) + 1
                }
                val newState = state.progress(time, minToWait)
                val stateAfterBuilt = newState.copy(
                    ore = newState.ore - geodeOrePrice,
                    obsidian = newState.obsidian - geodeObsidianPrice,
                    geodeProd = newState.geodeProd + 1
                )

                max = searchOres(time, stateAfterBuilt, highest).coerceAtLeast(max)
            }
            if (state.clayProd > 0) {
                val oresNeeded = obsidianOrePrice - state.ore
                val claysNeeded = obsidianClayPrice - state.clay
                val minToWait = if (oresNeeded <= 0 && claysNeeded <= 0) {
                    1
                } else {
                    ceil(oresNeeded / state.oreProd.toFloat()).toInt()
                        .coerceAtLeast(ceil(claysNeeded / state.clayProd.toFloat()).toInt()) + 1
                }
                val newState = state.progress(time, minToWait)
                val stateAfterBuilt = newState.copy(
                    ore = newState.ore - obsidianOrePrice,
                    clay = newState.clay - obsidianClayPrice,
                    obsidianProd = newState.obsidianProd + 1
                )

                max = searchOres(time, stateAfterBuilt, highest).coerceAtLeast(max)
            }
            if (state.oreProd > 0) {
                val oreNeeded = clayOrePrice - state.ore
                val minutesToWait = if (oreNeeded <= 0) {
                    1
                } else {
                    ceil(oreNeeded / state.oreProd.toFloat()).toInt() + 1
                }
                val newState = state.progress(time, minutesToWait)
                val buildRobo = newState.copy(ore = newState.ore - clayOrePrice, clayProd = newState.clayProd + 1)
                max = searchOres(time, buildRobo, highest).coerceAtLeast(max)
            }
            if (state.oreProd > 0) {
                val oreNeeded = oreOrePrice - state.ore
                val minutesToWait = if (oreNeeded <= 0) {
                    1
                } else {
                    ceil(oreNeeded / state.oreProd.toFloat()).toInt() + 1
                }
                val newState = state.progress(time, minutesToWait)
                val buildRobo = newState.copy(ore = newState.ore - oreOrePrice, oreProd = newState.oreProd + 1)
                max = searchOres(time, buildRobo, highest).coerceAtLeast(max)
            }
            return max
        }
    }

    private data class CollectState(
        val minute: Int,
        val ore: Int,
        val clay: Int,
        val obsidian: Int,
        val geode: Int,
        val oreProd: Int,
        val clayProd: Int,
        val obsidianProd: Int,
        val geodeProd: Int
    ) {
        fun progress(maxMinutes: Int, minutes: Int): CollectState {
            val steps = min(maxMinutes - minute, minutes)
            return copy(
                minute = minute + steps,
                ore = ore + steps * oreProd,
                clay = clay + steps * clayProd,
                obsidian = obsidian + steps * obsidianProd,
                geode = geode + steps * geodeProd
            )
        }
    }
}
