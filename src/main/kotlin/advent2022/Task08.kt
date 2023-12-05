package advent2022

import Task
import readInput

object Task08 : Task {
    override fun partA(): Int {
        var count = 0

        val forest = parseInput()
        forest.map { println(it) }

//        forest.forEachIndexed { rowIndex, row -> row.forEachIndexed { treeIndex, tree ->
//            val upper = forest.getOrNull(rowIndex - 1)?.getOrNull(treeIndex) ?: -1
//            val lower = forest.getOrNull(rowIndex + 1)?.getOrNull(treeIndex) ?: -1
//            val left = row.getOrNull(treeIndex - 1) ?: -1
//            val right = row.getOrNull(treeIndex + 1) ?: -1
//            val isEdge = upper == -1 || lower == -1 || left == -1 || right == -1
//
//            if ((tree > upper && tree > lower && tree > left && tree > right) || isEdge) ++count
//
//        } }

        return 0
    }

    override fun partB(): Int = 0

    private fun parseInput() = readInput("2022-08.txt").chunked(100)
}