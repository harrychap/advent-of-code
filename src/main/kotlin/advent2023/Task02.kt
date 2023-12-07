package advent2023

import Task
import utils.readInput

object Task02 : Task {

    override fun partA(): Int = parseInput().sumOf { (game, rounds) ->
        val invalidGame = rounds.any { round ->
            round.any {
                when (it.first) {
                    "red" -> it.second > 12
                    "blue" -> it.second > 14
                    "green" -> it.second > 13
                    else -> false
                }
            }
        }
        if (!invalidGame) game else 0
    }

    override fun partB(): Int = parseInput().sumOf { (_, rounds) ->
        var maxBlue = 1
        var maxRed = 1
        var maxGreen = 1
        rounds.flatten().forEach { picks ->
            when (picks.first) {
                "red" -> if (picks.second > maxRed) maxRed = picks.second
                "blue" -> if (picks.second > maxBlue) maxBlue = picks.second
                "green" -> if (picks.second > maxGreen) maxGreen = picks.second
            }
        }
        maxBlue * maxRed * maxGreen
    }


    private fun parseInput() =
        readInput("2023-02.txt").split("\n").map { line ->
            line.split(":").first().findInt() to line.split(":").last().split(";")
                .map { round -> round.split(",").map { pick -> pick.extractColour() to pick.findInt() } }
        }


    private fun String.findInt(): Int = this.filter { it.isDigit() }.toInt()
    private fun String.extractColour(): String? {
        when {
            this.contains("red") -> return "red"
            this.contains("blue") -> return "blue"
            this.contains("green") -> return "green"
        }
        return null
    }
}