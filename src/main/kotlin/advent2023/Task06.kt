package advent2023

import Task
import com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time
import utils.readInput
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.pow
import kotlin.text.Typography.times

object Task06 : Task {

    override fun partA(): Int {
        val times = parseInput().first()
        val distances = parseInput().last()

        return times.mapIndexed { index, time ->
            (0..time).count {
                (it * (time - it)) > distances[index]
            }
        }.reduce { accumulator, element ->
            accumulator * element
        }
    }

    override fun partB(): Int {
        val time = parseInput().first().fold("") { acc, i ->
            acc + i.toString()
        }.toBigDecimal()
        val distance = parseInput().last().fold("") { acc, i ->
            acc + i.toString()
        }.toBigDecimal()

        return (0..time.toInt()).count {
            (it.toBigDecimal() * (time - it.toBigDecimal())) > distance
        }
    }

    private fun parseInput() =
        readInput("2023-06.txt").split("\n").map { it.split(":").last().split(" ").mapNotNull { it.toIntOrNull() } }

}