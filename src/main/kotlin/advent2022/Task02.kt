package advent2022

import Task
import readInput

object Task02 : Task {

    private const val ROCK = 1
    private const val PAPER = 2
    private const val SCISSORS = 3

    private const val WIN = 6 // Z
    private const val DRAW = 3 // Y
    private const val LOSE = 0 // X

    private val handScorePartA = mapOf(
        "A X" to 4,
        "A Y" to 8,
        "A Z" to 3,
        "B X" to 1,
        "B Y" to 5,
        "B Z" to 9,
        "C X" to 7,
        "C Y" to 2,
        "C Z" to 6,
    )

    private val handScorePartB = mapOf(
        "A X" to LOSE + SCISSORS,
        "A Y" to DRAW + ROCK,
        "A Z" to WIN + PAPER,
        "B X" to LOSE + ROCK,
        "B Y" to DRAW + PAPER,
        "B Z" to WIN + SCISSORS,
        "C X" to LOSE + PAPER,
        "C Y" to DRAW + SCISSORS,
        "C Z" to WIN + ROCK,
    )

    override fun partA(): Int = parseInput().mapNotNull {
        handScorePartA[it]
    }.sum()

    override fun partB(): Int = parseInput().mapNotNull {
        handScorePartB[it]
    }.sum()

    private fun parseInput() = readInput("2022-02.txt").split("\n")

}