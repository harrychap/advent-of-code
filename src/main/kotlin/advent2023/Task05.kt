package advent2023

import Task
import utils.readInput

object Task05 : Task {

    override fun partA(): Long? {
        val seeds = parseSeeds()
        val ranges = parseInput()

        return seeds.minOfOrNull { seed ->
            seed.getMinLocation(ranges)
        }


    }
    override fun partB(): Long? {
        //TODO not working yet
        val seedRanges = parseSeeds().zipWithNext().map { it.first until it.second }
        val ranges = parseInput()
        return seedRanges.minOfOrNull { seedRange ->
            var min = Long.MAX_VALUE

            for(seed in seedRange) {
                val minLoc = seed.getMinLocation(ranges)
                if (minLoc < min) min = minLoc
            }
            min
        }
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


    private fun parseSeeds() = readInput("2023-05.txt").split("\n").first().split(":").last().trim().split(" ").map { it.toLong() }

    private fun parseInput() =
        readInput("2023-05.txt").split("\n\n").filter { it.contains("map") }.associate { maps ->
            val split = maps.split("map:")
            split.first().trim() to split.last().trim().split("\n").map { values -> values.split(" ").map { it.toLong() } }
        }
}