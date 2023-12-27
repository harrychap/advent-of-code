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
        //I give up with this part
        val numbersToCount = mutableListOf<Int>()
        val input = parseInput()
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { stringIndex, string ->
                if (string == "*") {
                    val numbers = mutableListOf<Int>()

                    val aboveLeft = input.getOrNull(rowIndex - 1)
                        ?.getOrNull((stringIndex - 1).coerceAtLeast(0))?.toIntOrNull()
                    val aboveRight = input.getOrNull(rowIndex - 1)
                        ?.subList(stringIndex, (stringIndex + 2).coerceAtMost(row.size))
                    val above = input.getOrNull(rowIndex - 1)
                        ?.getOrNull((stringIndex).coerceAtLeast(0))?.toIntOrNull()
                    val right = row.subList((stringIndex + 1).coerceAtMost(row.size), row.size).first().toIntOrNull()
                    val left = row.subList(0, stringIndex).reversed().takeUntil { it.isInt() }.firstOrNull()
                        ?.toIntOrNull()
                    val belowLeft = input.getOrNull(rowIndex + 1)
                        ?.getOrNull((stringIndex - 1).coerceAtLeast(0))?.toIntOrNull()
                    val belowRight = input.getOrNull(rowIndex + 1)
                        ?.subList(stringIndex, (stringIndex + 2).coerceAtMost(row.size))
                    val below = input.getOrNull(rowIndex + 1)
                        ?.getOrNull((stringIndex).coerceAtLeast(0))?.toIntOrNull()

                    if (aboveLeft != null && above == null) {
                        val aboveRow = input.getOrNull(rowIndex - 1) ?: emptyList()
                        val aboveNumbers = aboveRow.subList((stringIndex - 3).coerceAtLeast(0), (stringIndex + 1).coerceAtMost(aboveRow.size))
                        val fullNumberAbv = aboveNumbers.windowed(3, 1).mapNotNull { window ->
                            window.reversed().takeUntil { !it.isInt() }.filter { it.isInt() }.reversed().fold("") { acc, s -> "$acc$s" }.toIntOrNull()
                        }.max()
                        numbers.add(fullNumberAbv)
                    }

                    if (aboveRight?.any { it.isInt() } == true && above == null) {
                        val aboveRow = input.getOrNull(rowIndex - 1) ?: emptyList()
                        val aboveNumbers = aboveRow.subList((stringIndex + 1).coerceAtMost(aboveRow.size), (stringIndex + 4).coerceAtMost(aboveRow.size))
                        val fullNumberAbv = aboveNumbers.windowed(3, 1).mapNotNull { window ->
                            window.takeUntil { !it.isInt() }.filter { it.isInt() }.fold("") { acc, s -> "$acc$s" }.toIntOrNull()
                        }.first()
                        numbers.add(fullNumberAbv)
                    }

                    if (above != null) {
                        val aboveRow = input.getOrNull(rowIndex - 1) ?: emptyList()
                        val aboveNumbers = aboveRow.subList((stringIndex - 2).coerceAtLeast(0), (stringIndex + 3).coerceAtMost(aboveRow.size))
                        val fullNumberAbv = aboveNumbers.windowed(3, 1).mapNotNull { window ->
                            window.takeUntil { !it.isInt() }.filter { it.isInt() }.fold("") { acc, s -> "$acc$s" }.toIntOrNull()
                        }.max()
                        numbers.add(fullNumberAbv)
                    }

                    if (belowRight?.any { it.isInt() } == true && below == null) {
                        val belowRow = input.getOrNull(rowIndex + 1) ?: emptyList()
                        val belowNumbers = belowRow.subList((stringIndex + 1).coerceAtMost(belowRow.size), (stringIndex + 4).coerceAtMost(belowRow.size))
                        val fullNumberBel = belowNumbers.windowed(3, 1).firstNotNullOf { window ->
                            window.takeUntil { !it.isInt() }.filter { it.isInt() }.fold("") { acc, s -> "$acc$s" }
                                .toIntOrNull()
                        }
                        numbers.add(fullNumberBel)
                    }

                    if (belowLeft != null && below == null) {
                        val belowRow = input.getOrNull(rowIndex + 1) ?: emptyList()
                        val belowNumbers = belowRow.subList((stringIndex - 3).coerceAtLeast(0), (stringIndex + 1).coerceAtMost(belowRow.size))
                        val fullNumberBel = belowNumbers.windowed(3, 1).mapNotNull { window ->
                            val thing = window.reversed().takeUntil { !it.isInt() }.filter { it.isInt() }.reversed().fold("") { acc, s -> "$acc$s" }.toIntOrNull()
                            thing
                        }.last()
                        numbers.add(fullNumberBel)
                    }

                    if (below != null) {
                        val belowRow = input.getOrNull(rowIndex + 1) ?: emptyList()
                        val belowNumbers = belowRow.subList((stringIndex - 2).coerceAtLeast(0), (stringIndex + 3).coerceAtMost(belowRow.size))
                        val fullNumberBel = belowNumbers.windowed(3, 1).mapNotNull { window ->
                            window.filter { it.isInt() }.fold("") { acc, s -> "$acc$s" }.toIntOrNull()
                        }.max()
                        numbers.add(fullNumberBel)
                    }

                    if (right != null) {
                        val rightNumbers =
                            row.subList((stringIndex + 1).coerceAtMost(row.size), row.size).takeUntil { !it.isInt() }.filter { it.isInt() }
                        val fullNumberRight = (rightNumbers).fold("") { acc, s -> "$acc$s" }.toIntOrNull()
                        if (fullNumberRight != null) {
                            numbers.add(fullNumberRight)
                        }
                    }

                    if (left != null) {
                        val leftNumbers =
                            row.subList(0, stringIndex).reversed().takeUntil { !it.isInt() }.filter { it.isInt() }
                                .reversed()
                        val fullNumberLeft = (leftNumbers).fold("") { acc, s -> "$acc$s" }.toIntOrNull()
                        if (fullNumberLeft != null) {
                            numbers.add(fullNumberLeft)
                        }
                    }
                    if (numbers.size == 2) {
                        val ratio = numbers.fold(1) { acc, next -> acc * next }
                        numbersToCount.add(ratio)
                    }

                }
            }
        }

        return numbersToCount.sum()
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