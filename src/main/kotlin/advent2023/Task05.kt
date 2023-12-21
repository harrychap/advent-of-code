package advent2023

import Task
import utils.readInput
import kotlin.math.absoluteValue

object Task05 : Task {

    override fun partA(): Long? {
        val seeds = parseSeeds()
        val ranges = parseInput()

        return seeds.minOfOrNull { seed ->
            seed.getMinLocation(ranges)
        }
    }

    override fun partB(): Long {
        val seedRanges = parseSeeds().windowed(2, 2)
            .map { it[0] until it[0] + it[1] }
        val ranges = parseInput().mapValues { ranges ->
            ranges.value.map { range ->
                (range[1] until range[1] + range[2]) to (range[0] until range[0] + range[2])
            }
        }

        val seedToSoilRanges = ranges["seed-to-soil"]!!
        val soilToFertilizerRanges = ranges["soil-to-fertilizer"]!!
        val fertilizerToWaterRanges = ranges["fertilizer-to-water"]!!
        val waterToLightRanges = ranges["water-to-light"]!!
        val lightToTemperatureRanges = ranges["light-to-temperature"]!!
        val temperatureToHumidityRanges = ranges["temperature-to-humidity"]!!
        val humidityToLocationRanges = ranges["humidity-to-location"]!!

        var minValue = Long.MAX_VALUE

        for (location in 1 until 100_000_000L) {
            val humidity = humidityToLocationRanges.findPreviousValue(location)
            val temperature = temperatureToHumidityRanges.findPreviousValue(humidity)
            val light = lightToTemperatureRanges.findPreviousValue(temperature)
            val water = waterToLightRanges.findPreviousValue(light)
            val fertilizer = fertilizerToWaterRanges.findPreviousValue(water)
            val soil = soilToFertilizerRanges.findPreviousValue(fertilizer)
            val seed = seedToSoilRanges.findPreviousValue(soil)

            if (seedRanges.any { it.contains(seed) }) {
                minValue = location
                break
            }
        }

       return minValue
    }

    private fun Long.getMinLocation(ranges: Map<String, List<List<Long>>>): Long {
        var calc = this
        calc = ranges.findNextValue("seed-to-soil", calc)
        calc = ranges.findNextValue("soil-to-fertilizer", calc)
        calc = ranges.findNextValue("fertilizer-to-water", calc)
        calc = ranges.findNextValue("water-to-light", calc)
        calc = ranges.findNextValue("light-to-temperature", calc)
        calc = ranges.findNextValue("temperature-to-humidity", calc)
        calc = ranges.findNextValue("humidity-to-location", calc)
        return calc
    }

    private fun Map<String, List<List<Long>>>.findNextValue(mapString: String, seed: Long) = this[mapString]!!.firstNotNullOfOrNull { ranges ->
        if (seed >= ranges[1] && seed < ranges[1] + ranges[2]) {
            (seed - ranges[1]) + ranges[0]
        } else {
            null
        }
    } ?: seed

    private fun List<Pair<LongRange, LongRange>>.findPreviousValue(value: Long): Long {
        val ranges = this.firstOrNull { it.second.contains(value) }
            ?: return value

        return ranges.first.first + (value - ranges.second.first).absoluteValue
    }

    private fun parseSeeds() = readInput("2023-05.txt").split("\n").first().split(":").last().trim().split(" ").map { it.toLong() }

    private fun parseInput() =
        readInput("2023-05.txt").split("\n\n").filter { it.contains("map") }.associate { maps ->
            val split = maps.split("map:")
            split.first().trim() to split.last().trim().split("\n").map { values -> values.split(" ").map { it.toLong() } }
        }
}