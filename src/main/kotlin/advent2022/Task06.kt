package advent2022

import Task
import readInput

object Task06 : Task {
    override fun partA(): Int = parseInput().windowed(4, 1).indexOfFirst { it.isUniqueCharacters() } + 4

    override fun partB(): Int = parseInput().windowed(14, 1).indexOfFirst { it.isUniqueCharacters() } + 14

    private fun String.isUniqueCharacters() = toSet().size == length

    private fun parseInput() = readInput("2022-06.txt")
}