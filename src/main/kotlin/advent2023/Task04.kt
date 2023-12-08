package advent2023

import Task
import utils.readInput
import kotlin.math.pow

object Task04 : Task {

    override fun partA(): Int = parseInput().sumOf { (_, results) ->
        val count = results.last().count { results.first().contains(it) } - 1
        (1 shl count).coerceAtLeast(0)
    }


    override fun partB(): Int = 0 // TODO

    private fun parseInput() =
        readInput("2023-04.txt").split("\n")
            .map { line ->
                line.split(":") }.map { (card, results) ->
                    card.filter { it.isDigit() }.toInt() to results.split("|").map { numbers ->
                        numbers.chunked(3).mapNotNull { number ->
                            number.filter { it.isDigit() }.toIntOrNull()
                        }
                    }
                }
}