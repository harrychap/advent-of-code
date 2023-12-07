package advent2023

import Task
import utils.readInput

object Task01 : Task {

    private val numberMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
    )

    override fun partA(): Int = parseInput().sumOf { line ->
        "${line.find { it.isDigit() }}${line.findLast { it.isDigit() }}".toInt()
    }

    override fun partB(): Int = parseInput().sumOf { line ->

        val firstWordNumber = numberMap.keys.map {
            line.windowed(5, 1)
                .firstNotNullOfOrNull { window -> numberMap.keys.firstOrNull { key -> window.contains(key) } }
        }.firstOrNull() ?: "notfound"

        val lastWordNumber = numberMap.keys.map {
            line.windowed(5, 1)
                .mapNotNull { window -> numberMap.keys.lastOrNull { key -> window.contains(key) } }.lastOrNull()
        }
            .lastOrNull() ?: "notfound"

        val firstWord = numberMap[firstWordNumber]
        val lastWord = numberMap[lastWordNumber]
        val firstNumber = line.find { it.isDigit() }?.digitToIntOrNull()
        val lastNumber = line.findLast { it.isDigit() }?.digitToIntOrNull()

        val firstCoord =
            if (line.substringBefore(firstWordNumber).contains(firstNumber.toString())) firstNumber else firstWord
        val lastCoord =
            if (line.substringAfterLast(lastWordNumber).contains(lastNumber.toString())) lastNumber else lastWord

        "$firstCoord$lastCoord".toInt()
    }

    private fun parseInput() =
        readInput("2023-01.txt").split("\n")

}