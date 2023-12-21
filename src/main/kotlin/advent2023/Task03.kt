package advent2023

import Task
import utils.readInput

object Task03 : Task {

    override fun partA(): Int {
        val numbersToCount = mutableListOf<Int>()
        val input = parseInput()
        var skipCount = 0

        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { stringIndex, string ->
                if (string.isInt() && skipCount == 0) {
                    val column = input.getColumn(stringIndex)
                    val leftColumn = input.getColumn(stringIndex - 1)
                    val rightColumn = input.getColumn(stringIndex + 1)
                    val surroundingChars = listOfNotNull(
                        row.subList(0, stringIndex).reversed().takeUntil { !it.isInt() }.firstOrNull(),
                        row.subList(stringIndex, row.size).takeUntil { !it.isInt() }.lastOrNull(),
                        column.subList(rowIndex + 1, column.size).takeUntil { !it.isInt() }.firstOrNull(),
                        column.subList(0, rowIndex).reversed().takeUntil { !it.isInt() }.firstOrNull(),
                        leftColumn.getOrNull(rowIndex - 1),
                        leftColumn.getOrNull(rowIndex + 1),
                        rightColumn.getOrNull(rowIndex - 1),
                        rightColumn.getOrNull(rowIndex + 1)
                    )
                    if (surroundingChars.any { !it.isInt() && it != "." }) {
                        val leftNumbers =
                            row.subList(0, stringIndex).reversed().takeUntil { !it.isInt() }.filter { it.isInt() }
                                .reversed()
                        val rightNumbers =
                            row.subList(stringIndex, row.size).takeUntil { !it.isInt() }.filter { it.isInt() }
                        val fullNumber = (leftNumbers + rightNumbers).fold("") { acc, s -> "$acc$s" }.toInt()
                        skipCount = rightNumbers.size - 1
                        numbersToCount.add(fullNumber)
                    }
                } else if (skipCount > 0) {
                    --skipCount
                }
            }
        }

        return numbersToCount.sum()
    }

    override fun partB(): Int {
        val numbersToCount = mutableListOf<Int>()
        val input = parseInput()
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { stringIndex, string ->
                if (string == "*") {
                    var firstNo = 0
                    var secondNo = 0

                    //Above

                    //Right
                    val right = row.subList(stringIndex, row.size).first().toIntOrNull()
                    //Left
                    val left = row.subList(0, stringIndex).reversed().takeUntil { it.isInt() }.firstOrNull()
                    //Below
                }
            }
        }
        return 0
    }

    private fun List<List<String>>.getColumn(col: Int): List<String?> =
        Array(this.size) { this.getOrNull(it)?.getOrNull(col) }.toList()

    private fun String?.isInt(): Boolean = this?.toIntOrNull() != null

    private fun <T> Iterable<T>.takeUntil(predicate: (T) -> Boolean): List<T> {
        val list = ArrayList<T>()
        for (item in this) {
            list.add(item)
            if (predicate(item))
                break
        }
        return list
    }

    private fun parseInput() =
        readInput("2023-03.txt").split('\n').map { line -> line.map { it.toString() } }

}