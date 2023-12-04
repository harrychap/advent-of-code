package advent2022

import Task
import java.io.File

object Task01 : Task {

    override fun partA(): Int {
        var highestValue = 0
        var currentValue = 0

        parseInput().forEach {
            currentValue += it ?: 0
            if (it == null && currentValue > highestValue) {
                highestValue = currentValue
                currentValue =  0
            } else if (it == null) {
                currentValue = 0
            }
        }

        return highestValue

    }

    override fun partB(): Int {
        var highestValue = 0
        var currentValue = 0
        val allValues = mutableListOf<Int>()
        parseInput().forEach {
            currentValue += it ?: 0
            if (it == null && currentValue > highestValue) {
                highestValue = currentValue
                allValues.add(currentValue)
                currentValue =  0
            } else if (it == null) {
                allValues.add(currentValue)
                currentValue = 0
            }
        }

        return allValues.sortedDescending().take(3).sum()
    }

    private fun parseInput() = File("src/main/input/2022-01.txt").readLines().map { it.toIntOrNull() }

}