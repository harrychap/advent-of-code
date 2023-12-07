package advent2022

import Task
import utils.readInput

object Task05 : Task {

    private fun getStack() = mapOf(
        1 to mutableListOf('J', 'H', 'P', 'M', 'S', 'F', 'N', 'V'),
        2 to mutableListOf('S', 'R', 'L', 'M', 'J', 'D', 'Q'),
        3 to mutableListOf('N', 'Q', 'D', 'H', 'C', 'S', 'W', 'B'),
        4 to mutableListOf('R', 'S', 'C', 'L'),
        5 to mutableListOf('M', 'V', 'T', 'P', 'F', 'B'),
        6 to mutableListOf('T', 'R', 'Q', 'N', 'C'),
        7 to mutableListOf('G', 'V', 'R'),
        8 to mutableListOf('C', 'Z', 'S', 'P', 'D', 'L', 'R'),
        9 to mutableListOf('D', 'S', 'J', 'V', 'G', 'P', 'B', 'F'),
    )
    override fun partA(): List<Char> {
        val partAStack = getStack()
        parseInput().forEach { (move, from, to) ->
            repeat(move) {
                val last = partAStack[from]?.last()
                if (last != null){
                    partAStack[to]?.add(last)
                    partAStack[from]?.removeLast()
                }
            }
        }
        return partAStack.map { it.value.last() }
    }

    override fun partB(): List<Char> {
        val partBStack = getStack()
        parseInput().forEach { (move, from, to) ->
            val last = partBStack[from]?.takeLast(move)
            last?.forEach {
                partBStack[to]?.add(it)
                partBStack[from]?.removeLast()
            }
        }
        return partBStack.map { it.value.last() }
    }

    private fun parseInput() = readInput("2022-05.txt").split("\n\n").last().split('\n')
        .map { it.split(' ').mapNotNull { it.toIntOrNull() } }
}