package advent2022

import Task
import utils.readInput
import utils.takeUntil

object Task08 : Task {
    override fun partA(): Int {
        val forest = parseInput()
        return forest.mapIndexed { rowIndex, row -> row.mapIndexedNotNull { treeIndex, tree ->
            val column = getColumn(forest, treeIndex)
            val downColumn = column.subList(rowIndex + 1, column.size)
            val upColumn = column.subList(0, rowIndex)
            val leftRow = row.subList(0, treeIndex)
            val rightRow = row.subList(treeIndex + 1, row.size)

            if (!downColumn.any { it >= tree } || !upColumn.any { it >= tree } || !leftRow.any { it >= tree } || !rightRow.any { it >= tree }) {
                tree
            } else null
        } }.flatten().count()
    }

    override fun partB(): Int {
        val forest = parseInput()
        return forest.mapIndexed { rowIndex, row ->
            row.mapIndexed { treeIndex, tree ->
                val column = getColumn(forest, treeIndex)
                val downScore = column.subList(rowIndex + 1, column.size).takeUntil { it >= tree }.count()
                val upScore = column.subList(0, rowIndex).reversed().takeUntil { it >= tree }.count()
                val leftScore = row.subList(0, treeIndex).reversed().takeUntil { it >= tree }.count()
                val rightScore = row.subList(treeIndex + 1, row.size).takeUntil { it >= tree }.count()
                upScore * leftScore * downScore * rightScore
            }
        }.flatten().max()
    }

    private fun parseInput() = readInput("2022-08.txt").split('\n').map { it.map { it.digitToInt() } }

    private fun getColumn(matrix: List<List<Int>>, col: Int): List<Int> = IntArray(matrix.size) { matrix[it][col] }.toList()

}