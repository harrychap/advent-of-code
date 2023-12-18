package advent2023

import Task
import utils.readInput

object Task09 : Task {

    private fun history(sequence: List<Int>): List<List<Int>> =
        buildList {
            add(sequence)
            do {
                add((1..<last().size).map { i -> last()[i] - last()[i - 1] })
            } while (last().any { it != 0 })
            reverse()
        }

    override fun partA(): Int = parseInput().sumOf { line ->
        history(line).fold(0) { acc, ints -> acc + ints.last() }.toInt()
    }

    override fun partB(): Int = parseInput().sumOf { line ->
        history(line).fold(0) { acc, ints ->
            ints[0] - acc
        }.toInt()
    }

    private fun parseInput() =
        readInput("2023-09.txt").split("\n").map { line -> line.split(" ").map { it.toInt() } }
}