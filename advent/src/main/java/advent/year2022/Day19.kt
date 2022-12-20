package advent.year2022

import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.ceil
import kotlin.math.min

class Day19(input: String) {
    val blueprints: List<Blueprint>

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

    data class Blueprint(
        val id: Int,
        val oreOrePrice: Int,
        val clayOrePrice: Int,
        val obsidianOrePrice: Int,
        val obsidianClayPrice: Int,
        val geodeOrePrice: Int,
        val geodeObsidianPrice: Int
    ) {
        val oreRoboPrice = Price(PartState(oreOrePrice, 0, 0, 0), PartState(1, 0, 0, 0))
        val clayRoboPrice = Price(PartState(clayOrePrice, 0, 0, 0), PartState(0, 1, 0, 0))
        val obsRoboPrice = Price(PartState(obsidianOrePrice, obsidianClayPrice, 0, 0), PartState(0, 0, 1, 0))
        val geoRoboPrice = Price(PartState(geodeOrePrice, 0, geodeObsidianPrice, 0), PartState(0, 0, 0, 1))

        fun searchStepBased(time: Int): Int = searchStepBased(
            time,
            ListState(0, PartState(0, 0, 0, 0), PartState(1, 0, 0, 0)),
            AtomicInteger(0)
        )

        private fun searchStepBased(time: Int, state: ListState, highest: AtomicInteger): Int {
            if (state.time == time) {
                return highest.updateAndGet { state.minerals.geo.coerceAtLeast(it) }
            }
            val minuteLeft = time - state.time
            val maxCanProduce = state.minerals.geo + (0 until minuteLeft).sumOf { state.robots.geo + it }
            if (maxCanProduce < highest.get()) return 0
            val onBuy: (ListState) -> Unit = { newState -> searchStepBased(time, newState, highest) }
            if (state.canBuy(geoRoboPrice)) {
                onBuy(state.progressWithPurchase(geoRoboPrice))
            } else if (state.robots.obs == 0 && state.canBuy(obsRoboPrice)) {
                onBuy(state.progressWithPurchase(obsRoboPrice))
            } else {
                state.buy(obsRoboPrice, onBuy)
                state.buy(clayRoboPrice, onBuy)
                state.buy(oreRoboPrice, onBuy)
                onBuy(state.progress())
            }
            return highest.get()
        }

        fun searchDecisionBased(time: Int): Int = searchDecisionBased(
            time,
            ListState(0, PartState(0, 0, 0, 0), PartState(1, 0, 0, 0)),
            AtomicInteger(0)
        )

        private fun searchDecisionBased(time: Int, state: ListState, highest: AtomicInteger): Int {
            if (state.time == time) return highest.updateAndGet { state.minerals.geo.coerceAtLeast(it) }
            val minuteLeft = time - state.time
            val maxCanProduce = state.minerals.geo + (0 until minuteLeft).sumOf { state.robots.geo + it }
            if (maxCanProduce < highest.get()) return 0

            if (state.robots.obs > 0) {
                proceedUntilCanBuy(state, geoRoboPrice, time) { next -> searchDecisionBased(time, next, highest) }
            }
            if (state.robots.clay > 0) {
                proceedUntilCanBuy(state, obsRoboPrice, time) { next -> searchDecisionBased(time, next, highest) }
            }
            proceedUntilCanBuy(state, clayRoboPrice, time) { next -> searchDecisionBased(time, next, highest) }
            proceedUntilCanBuy(state, oreRoboPrice, time) { next -> searchDecisionBased(time, next, highest) }
            return highest.get()
        }

        private inline fun proceedUntilCanBuy(
            state: ListState,
            price: Price,
            maxTime: Int,
            crossinline onContinue: (ListState) -> Unit
        ) {
            var next = state
            while (!next.canBuy(price)) {
                next = next.progress()
                if (next.time == maxTime) {
                    onContinue(next)
                    return
                }
            }
            onContinue(next.progressWithPurchase(price))
        }

        fun searchFast(time: Int): Int {
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
            return searchFast(time, startState, AtomicInteger(0))
        }

        private fun searchFast(time: Int, state: CollectState, highest: AtomicInteger): Int {
            if (state.minute >= time) {
                return highest.updateAndGet { state.geode.coerceAtLeast(it) }
            }
            val minuteLeft = time - state.minute
            val maxCanProduce = state.geode + (0 until minuteLeft).sumOf { state.geodeProd + it }
            if (maxCanProduce < highest.get()) return 0

            if (state.obsidianProd > 0) {
                val oresNeeded = geodeOrePrice - state.ore
                val obsNeeded = geodeObsidianPrice - state.obsidian
                val minToWait = if (oresNeeded <= 0 && obsNeeded <= 0) {
                    1
                } else {
                    ceil(oresNeeded.coerceAtLeast(0) / state.oreProd.toFloat()).toInt()
                        .coerceAtLeast(ceil(obsNeeded.coerceAtLeast(0) / state.obsidianProd.toFloat()).toInt()) + 1
                }
                val newState = state.progress(time, minToWait)
                val stateAfterBuilt = newState.copy(
                    ore = newState.ore - geodeOrePrice,
                    obsidian = newState.obsidian - geodeObsidianPrice,
                    geodeProd = newState.geodeProd + 1
                )

                searchFast(time, stateAfterBuilt, highest)
            }
            if (state.clayProd > 0) {
                val oresNeeded = obsidianOrePrice - state.ore
                val claysNeeded = obsidianClayPrice - state.clay
                val minToWait = if (oresNeeded <= 0 && claysNeeded <= 0) {
                    1
                } else {
                    ceil(oresNeeded.coerceAtLeast(0) / state.oreProd.toFloat()).toInt()
                        .coerceAtLeast(ceil(claysNeeded.coerceAtLeast(0) / state.clayProd.toFloat()).toInt()) + 1
                }
                val newState = state.progress(time, minToWait)
                val stateAfterBuilt = newState.copy(
                    ore = newState.ore - obsidianOrePrice,
                    clay = newState.clay - obsidianClayPrice,
                    obsidianProd = newState.obsidianProd + 1
                )

                searchFast(time, stateAfterBuilt, highest)
            }
            if (state.oreProd > 0) {
                val oreNeeded = (clayOrePrice - state.ore).coerceAtLeast(0)
                val minutesToWait = if (oreNeeded <= 0) {
                    1
                } else {
                    ceil(oreNeeded / state.oreProd.toFloat()).toInt() + 1
                }
                val newState = state.progress(time, minutesToWait)
                val buildRobo = newState.copy(ore = newState.ore - clayOrePrice, clayProd = newState.clayProd + 1)
                searchFast(time, buildRobo, highest)
            }
            if (state.oreProd > 0) {
                val oreNeeded = (oreOrePrice - state.ore).coerceAtLeast(0)
                val minutesToWait = if (oreNeeded <= 0) {
                    1
                } else {
                    ceil(oreNeeded / state.oreProd.toFloat()).toInt() + 1
                }
                val newState = state.progress(time, minutesToWait)
                val buildRobo = newState.copy(ore = newState.ore - oreOrePrice, oreProd = newState.oreProd + 1)
                searchFast(time, buildRobo, highest)
            }

            return highest.get()
        }
    }

    data class ListState(
        val time: Int,
        val minerals: PartState,
        val robots: PartState
    ) {
        fun progress(): ListState =
            ListState(time + 1, minerals + robots, robots)

        fun canBuy(price: Price): Boolean = minerals.enoughFor(price.price)

        inline fun buy(price: Price, onBuy: (ListState) -> Unit) {
            if (canBuy(price)) onBuy(progressWithPurchase(price))
        }

        fun progressWithPurchase(price: Price): ListState =
            ListState(time + 1, minerals - price.price + robots, robots + price.update)
    }

    data class PartState(val ore: Int, val clay: Int, val obs: Int, val geo: Int) {
        operator fun minus(other: PartState): PartState =
            PartState(ore - other.ore, clay - other.clay, obs - other.obs, geo - other.geo)

        operator fun plus(other: PartState): PartState =
            PartState(ore + other.ore, clay + other.clay, obs + other.obs, geo + other.geo)

        fun enoughFor(other: PartState): Boolean =
            ore >= other.ore && clay >= other.clay && obs >= other.obs && geo >= other.geo

        override fun toString(): String = "(ore=$ore, clay=$clay, obs=$obs, geo=$geo)"
    }

    data class Price(val price: PartState, val update: PartState) {
        operator fun plus(other: Price): Price = Price(price + other.price, update + other.update)
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
