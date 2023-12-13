package advent2023

import Task
import advent2023.Task07.isFourOfAKind
import advent2023.Task07.isTwoPair
import utils.readInput
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.pow

object Task07 : Task {

    private val cardOrder = listOf("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2")
    private val cardOrderWithJoker = listOf("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J")

    override fun partA(): Int = calculateTotal(cardOrder)

    override fun partB(): Int = calculateTotal(cardOrderWithJoker)

    private fun calculateTotal(cardOrder: List<String>): Int {
        val fiveOfAKinds = mutableListOf<Pair<String, Int>>()
        val fourOfAKinds = mutableListOf<Pair<String, Int>>()
        val threeOfAKinds = mutableListOf<Pair<String, Int>>()
        val fullHouses = mutableListOf<Pair<String, Int>>()
        val twoPairs = mutableListOf<Pair<String, Int>>()
        val pairs = mutableListOf<Pair<String, Int>>()
        val distinct = mutableListOf<Pair<String, Int>>()

        parseInput().forEach { (hand, bid) ->
            if (cardOrder.last() == "J") {
                val handsWithJoker = cardOrder.map { hand.replace("J", it) }
                when {
                    handsWithJoker.any { it.isFiveOfAKind() } -> fiveOfAKinds.add(hand to bid)
                    handsWithJoker.any { it.isFourOfAKind() } -> fourOfAKinds.add(hand to bid)
                    handsWithJoker.any { it.isThreeOfAKind() } -> threeOfAKinds.add(hand to bid)
                    handsWithJoker.any { it.isFullHouse() } -> fullHouses.add(hand to bid)
                    handsWithJoker.any { it.isTwoPair() } -> twoPairs.add(hand to bid)
                    handsWithJoker.any { it.isPair() } -> pairs.add(hand to bid)
                    else -> distinct.add(hand to bid)
                }
            } else {
                when {
                    hand.isFiveOfAKind() -> fiveOfAKinds.add(hand to bid)
                    hand.isFourOfAKind() -> fourOfAKinds.add(hand to bid)
                    hand.isThreeOfAKind() -> threeOfAKinds.add(hand to bid)
                    hand.isFullHouse() -> fullHouses.add(hand to bid)
                    hand.isTwoPair() -> twoPairs.add(hand to bid)
                    hand.isPair() -> pairs.add(hand to bid)
                    else -> distinct.add(hand to bid)
                }
            }
        }

        val allCardsOrdered = mutableListOf<Pair<String, Int>>()

        distinct.sortCards(cardOrder).forEach { allCardsOrdered.add(it) }
        pairs.sortCards(cardOrder).forEach { allCardsOrdered.add(it) }
        twoPairs.sortCards(cardOrder).forEach { allCardsOrdered.add(it) }
        threeOfAKinds.sortCards(cardOrder).forEach { allCardsOrdered.add(it) }
        fullHouses.sortCards(cardOrder).forEach { allCardsOrdered.add(it) }
        fourOfAKinds.sortCards(cardOrder).forEach { allCardsOrdered.add(it) }
        fiveOfAKinds.sortCards(cardOrder).forEach { allCardsOrdered.add(it) }

        return allCardsOrdered.sumOf { (key, value) -> (allCardsOrdered.indexOf(key to value) + 1) * value }
    }

    private fun MutableList<Pair<String, Int>>.sortCards(cardOrder: List<String>) =
        this.sortedWith(compareByDescending<Pair<String, Int>> { cardOrder.indexOf(it.first[0].toString()) }.thenByDescending {
            cardOrder.indexOf(
                it.first[1].toString()
            )
        }.thenByDescending {
            cardOrder.indexOf(
                it.first[2].toString()
            )
        }.thenByDescending {
            cardOrder.indexOf(
                it.first[3].toString()
            )
        }.thenByDescending {
            cardOrder.indexOf(
                it.first[4].toString()
            )
        })

    private fun String.countChars(): Map<Char, Int> = this.associate { char -> char to this.count { it == char } }

    private fun String.isFiveOfAKind(): Boolean = this.countChars().all { (_, value) -> value == 5 }

    private fun String.isFourOfAKind(): Boolean = this.countChars().any { (_, value) -> value == 4 }

    private fun String.isThreeOfAKind(): Boolean =
        this.countChars().any { (_, value) -> value == 3 } && !this.isFullHouse()

    private fun String.isFullHouse(): Boolean =
        this.countChars().any { (_, value) -> value == 3 } && this.countChars().any { (_, value) -> value == 2 }

    private fun String.isTwoPair(): Boolean = this.countChars()
        .count { (_, value) -> value >= 2 } == 2 && !this.isThreeOfAKind() && !this.isFullHouse() && !this.isPair()

    private fun String.isPair(): Boolean = this.countChars()
        .count { (_, value) -> value >= 2 } == 1 && !this.isThreeOfAKind() && !this.isFullHouse() && !this.isTwoPair()

    private fun parseInput() =
        readInput("2023-07.txt").split("\n")
            .associate { round -> round.split(" ").first() to round.split(" ").last().filter { it.isDigit() }.toInt() }
}