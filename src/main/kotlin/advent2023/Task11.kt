package advent2023

import Task
import utils.readInput
import kotlin.math.abs


object Task11 : Task {

    data class Point(val x: Int, val y: Int, val type: PointType)

    enum class PointType {
        EMPTY,
        GALAXY
    }

    private fun List<Point>.uniquePairs(): List<Pair<Point, Point>> {
        val pairs = mutableListOf<Pair<Point, Point>>()

        this.forEachIndexed { leftIndex, leftPoint ->
            this.forEachIndexed { rightIndex, rightPoint ->
                if (rightIndex < leftIndex) pairs.add(Pair(leftPoint, rightPoint))
            }
        }

        return pairs
    }

    override fun partA(): Int {
        return parseInput().expand(1).uniquePairs().sumOf { (left, right) ->
            (abs(left.x - right.x) + abs(left.y - right.y))
        }
    }

    override fun partB(): Int {
        return parseInput().expand(1_000_001).uniquePairs().sumOf { (left, right) ->
            (abs(left.x - right.x) + abs(left.y - right.y))
        }
    }

    private fun List<Point>.expand(repeatBy: Int): List<Point> {
        val rowsToExpand =
            this.groupBy { it.y }.filter { it.value.all { point -> point.type == PointType.EMPTY } }.map { it.key }
        val columnsToExpand =
            this.groupBy { it.x }.filter { it.value.all { point -> point.type == PointType.EMPTY } }.map { it.key }

        var expanded = this.filter { it.type == PointType.GALAXY }

        rowsToExpand.forEach { rowNum ->
            repeat(repeatBy) {
                expanded = expanded.map {
                    if (it.y <= rowNum) {
                        it.copy(x = it.x, y = it.y - 1)
                    } else {
                        it
                    }
                }
            }
        }

        columnsToExpand.forEach { colNum ->
            repeat(repeatBy) {
                expanded = expanded.map {
                    if (it.x <= colNum) {
                        it.copy(x = it.x - 1, y = it.y)
                    } else {
                        it
                    }
                }
            }
        }
        return expanded
    }

    private fun parseInput() = readInput("2023-11.txt").split("\n").flatMapIndexed { y, row ->
        row.mapIndexed { x, char ->
            Point(
                x,
                y,
                if (char == '.') PointType.EMPTY else PointType.GALAXY
            )
        }
    }
}