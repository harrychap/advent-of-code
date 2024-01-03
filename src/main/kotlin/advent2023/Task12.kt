package advent2023

import Task
import utils.readInput

object Task12 : Task {

    override fun partA(): Int = parseInput().sumOf { (pattern, nums) ->
        val combinations = pattern.combinations().map { combination ->
            combination.joinToString("").split('.').filter { it.contains('#') }.map { it.length }
        }
        combinations.count { it == nums }
    }

    override fun partB(): Int = 0
//        TODO: Not working yet.
//        parseInput().sumOf { (pattern, nums) ->
//        val unfoldedPattern = mutableListOf<Char>()
//        val unFoldedNums = mutableListOf<Int>()
//        repeat(5) {
//            unfoldedPattern.addAll(pattern)
//            unFoldedNums.addAll(nums)
//        }
//        val combinations = unfoldedPattern.combinations().map { combination ->
//            combination.joinToString("").split('.').filter { it.contains('#') }.map { it.length }
//        }
//        combinations.count { it == unFoldedNums }
//    }

    private fun <T> permutations(length: Int, components: List<T>): List<List<T>> {
        return if (length <= 0) listOf(emptyList())
        else return permutations(length - 1, components)
            .flatMap { sub -> components.map { sub + it } }
    }

    private fun List<Char>.combinations(): List<List<Char>> {
        val unknownCount = this.count { it == '?' }
        val unknownCombinations = permutations(unknownCount, listOf('.', '#'))

        val combinations = mutableListOf<List<Char>>()
        unknownCombinations.forEach { unknownCombination ->
            val origCombi = this.toMutableList()
            unknownCombination.forEach { char ->
                val index = origCombi.indexOfFirst { it == '?' }
                if (index >= 0) origCombi[index] = char
            }
            combinations.add(origCombi)
        }

        return combinations
    }

    private fun parseInput() =
        readInput("2023-12.txt").split("\n").map { line ->
            val (pattern, nums) = line.split(" ")
            pattern.map { it } to nums.split(",").map { it.toInt() }
        }
}