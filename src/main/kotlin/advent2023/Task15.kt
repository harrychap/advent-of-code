package advent2023

import Task
import utils.readInput

object Task15 : Task {

    override fun partA(): Int = parseInput().sumOf { it.algo() }

    override fun partB(): Int {
        val boxes = (0 until 256).associateWith { mutableListOf<Pair<String, Int>>() }
        parseInput().forEach { seq ->
            val split = seq.split("-", "=")
            val label = split.first()
            val boxNo = label.algo()
            val focalLeng = split.last().toIntOrNull() ?: 0
            val box = boxes[boxNo]!!

            if (seq.contains("-")) {
                box.removeIf { (l, _) -> l == label }
            }

            if (seq.contains("=") && box.any { it.first == label }) {
                box[box.indexOfFirst { it.first == label }] = Pair(label, focalLeng)
            }

            if (seq.contains("=") && box.none { it.first == label }) {
                box.add(Pair(label, focalLeng))
            }
        }

        return boxes.filter { it.value.isNotEmpty() }.flatMap { box ->
            box.value.mapIndexed { index, contents ->
                (box.key + 1) * (index + 1) * contents.second
            }
        }.sum()
    }

    private fun String.algo(): Int {
        var currentVal = 0
        this.forEach { char ->
            currentVal += char.code
            currentVal *= 17
            currentVal %= 256
        }
        return currentVal
    }

    private fun parseInput() =
        readInput("2023-15.txt").split(",")
}