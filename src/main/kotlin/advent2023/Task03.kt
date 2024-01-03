package advent2023

import Task
import utils.readInput
import utils.takeUntil

object Task03 : Task {

    override fun partA(): Int {
        val numbersToCount = mutableListOf<Int>()
        val input = parseInput()

        input.forEach { point ->
            if (!point.value.isDigit() && point.value != '.') {
                val surroundingPoints = point.surroundingPoints(input)
                if (surroundingPoints.isNotEmpty()) {
                    val fullNumbers = surroundingPoints.map { it.expandNumber(input) }.toSet().map { points -> points.fold("") { acc, p -> "$acc${p.value}" }.toInt() }
                    numbersToCount.addAll(fullNumbers)
                }
            }
        }

        return numbersToCount.sum()
    }

    override fun partB(): Int {
        val numbersToCount = mutableListOf<Int>()
        val input = parseInput()

        input.forEach { point ->
            if (point.value == '*') {
                val surroundingPoints = point.surroundingPoints(input)
                if (surroundingPoints.isNotEmpty()) {
                    val fullNumbers = surroundingPoints.map { it.expandNumber(input) }.toSet().map { points -> points.fold("") { acc, p -> "$acc${p.value}" }.toInt() }
                    if (fullNumbers.size == 2) numbersToCount.add(fullNumbers.first() * fullNumbers.last())
                }
            }
        }

        return numbersToCount.sum()
    }

    data class Point(val x: Int, val y: Int, val value: Char)

    private fun Point.surroundingPoints(points: List<Point>): List<Point> = listOfNotNull(
        points.find { it.x == this.x && it.y == this.y - 1 },
        points.find { it.x == this.x - 1 && it.y == this.y },
        points.find { it.x == this.x && it.y == this.y + 1 },
        points.find { it.x == this.x + 1 && it.y == this.y },
        points.find { it.x == this.x + 1 && it.y == this.y + 1 },
        points.find { it.x == this.x - 1 && it.y == this.y - 1 },
        points.find { it.x == this.x + 1 && it.y == this.y - 1 },
        points.find { it.x == this.x - 1 && it.y == this.y + 1 }
    ).filter { it.value.isDigit() }

    private fun Point.expandNumber(points: List<Point>): List<Point> {
        val index = points.indexOf(this)
        val leftNumbers = points.subList(0, index).reversed().takeUntil { !it.value.isDigit() }.filter { it.value.isDigit() }.reversed()
        val rightNumbers = points.subList(index, points.size).takeUntil { !it.value.isDigit() }.filter { it.value.isDigit() }
        return (leftNumbers + rightNumbers)
    }

    private fun parseInput() =
        readInput("2023-03.txt").split("\n").flatMapIndexed { y, row ->
            row.mapIndexed { x, char ->
                Point(
                    x,
                    y,
                    char
                )
            }
        }
}