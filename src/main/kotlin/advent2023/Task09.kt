package advent2023

import Task
import utils.readInput


object Task09 : Task {

    override fun partA(): Int {
        val thing = parseInput().map { line ->
            var diff = line.zipWithNext { a, b ->  b - a }
            println(diff)



            if (diff.all { it == diff.first() }) line.last() + diff.first() else diff


        }
        println(thing)
        return 0
    }

    override fun partB(): Int {
        return 0
    }

    private fun parseInput() =
        readInput("2023-09.txt").split("\n").map { line -> line.split(" ").map { it.toInt() } }
}