package advent2023

import Task
import utils.readInput

object Task08 : Task {

    override fun partA(): Int {
        val instructions = parseInputInstructions()
        val values = parseInput()
        var count = 0
        var currentPosition = "AAA"
        do {
            instructions.forEach {
                val (left, right) = values[currentPosition]!!
                if (it == "L") currentPosition = left
                if (it == "R") currentPosition = right
                ++count
            }
        } while (currentPosition != "ZZZ")
        return count
    }

    override fun partB(): Long {
        val instructions = parseInputInstructions()
        val values = parseInput()

        val moves = values.keys.filter { it.endsWith("A") }.map {start ->
            var count = 0L
            var currentPos = start
            do {
                instructions.forEach { instruction ->
                    count++
                    val (left, right) = values[currentPos]!!
                    if (instruction == "L") currentPos = left
                    if (instruction == "R") currentPos = right
                    }
            } while (!currentPos.endsWith("Z"))
            count
        }

        return calcSteps(moves)
    }

    private fun calcSteps(steps: List<Long>): Long {
        val minStepCount = steps.min()
        var currentSteps = minStepCount
        do {
            currentSteps += minStepCount
        } while (steps.any { (currentSteps % it) != 0L })

        return currentSteps
    }

    private fun parseInputInstructions() =
        readInput("2023-08.txt").split("\n\n").first().map { it.toString() }

    private fun parseInput() =
        readInput("2023-08.txt").split("\n\n").last().split("\n").associate { line ->
            val equalsSplit = line.split("=")
            val values = equalsSplit.last().replace("(", "").replace(")", "").split(",").map { it.trim() }
            equalsSplit.first().trim() to Pair(values.first(), values.last()) }
}