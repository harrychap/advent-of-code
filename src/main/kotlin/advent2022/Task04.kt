package advent2022

import Task
import readInput

object Task04 : Task {
    override fun partA(): Int = parseInput().map {
        it.split(",").map { it.split("-").map { it.toInt() } }.map { (from, to) -> (from..to).toSet() }
    }.count { (first, second) ->
        val intersect = first.intersect(second)
        first.all { intersect.contains(it) } || second.all { intersect.contains(it) }
    }


    override fun partB(): Int = 0


    private fun parseInput() = readInput("2022-04.txt").split("\n")

}