package advent2022

import Task
import utils.readInput

object Task03 : Task {
    private val priority = ('a'..'z') + ('A'..'Z')

    override fun partA(): Int = parseInput().map {
        val first = it.substring(0, it.length/2)
        val second = it.substring(it.length/2)
        first.toSet().intersect(second.toSet())
    }.flatten().sumOf { priority.indexOf(it) + 1 }


    override fun partB(): Int = parseInput().chunked(3).map {
        val first = it[0].toSet()
        val second = it[1].toSet()
        val third = it[2].toSet()
        first.intersect(second).intersect(third)
    }.flatten().sumOf { priority.indexOf(it) + 1 }


    private fun parseInput() = readInput("2022-03.txt").split("\n")

}