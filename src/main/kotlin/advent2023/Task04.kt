package advent2023

import Task
import utils.readInput
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.pow

object Task04 : Task {

    override fun partA(): Int = parseInput().sumOf { (_, results) ->
        val count = results.last().count { results.first().contains(it) } - 1
        (1 shl count).coerceAtLeast(0)
    }

    override fun partB(): Int {
        val cardsAndScores = parseInput().associate { (game, results) ->
            game to results.last().count { results.first().contains(it) }
        }

        val cardsToProcess = PriorityQueue<Int>()
        cardsToProcess.addAll(cardsAndScores.keys)

        var cardCount = 0

        while (cardsToProcess.isNotEmpty()) {
            ++cardCount
            val cardNumber = cardsToProcess.poll()
            val cardNoOfWins = cardsAndScores[cardNumber] ?: 0

            repeat(cardNoOfWins) { cardNo ->
                val nextCard = cardNumber + (cardNo + 1)
                cardsAndScores[nextCard].let { cardsToProcess.add(nextCard) }
            }
        }

        return cardCount
    }

    private fun parseInput() =
        readInput("2023-04.txt").split("\n")
            .map { line ->
                line.split(":") }.map { (card, results) ->
                    card.filter { it.isDigit() }.toInt() to results.split("|").map { numbers ->
                        numbers.chunked(3).mapNotNull { number ->
                            number.filter { it.isDigit() }.toIntOrNull()
                        }
                    }
                }
}