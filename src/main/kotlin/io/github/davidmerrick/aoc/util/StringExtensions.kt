package io.github.davidmerrick.aoc.util

fun String.wordCount(delimiter: String = " "): Map<String, Int> =
    this.split(delimiter).groupingBy { it }.eachCount()

fun String.charCount(): Map<Char, Int> =
    this.groupingBy { it }.eachCount()

fun String.charsToPosition(): Map<Int, Char> =
    this.mapIndexed { i, char -> i to char }
        .toMap()

fun String.mostCommonChar(): Char = this.charCount()
    .maxBy { it.value }
    .key

fun String.leastCommonChar(): Char = this.charCount()
    .minBy { it.value }
    .key

fun String.isPalindrome(): Boolean = this.reversed() == this

fun String.getPalindromes(length: Int): List<String> {
    val palindromes = mutableListOf<String>()
    this.asSequence()
        .windowed(length)
        .map { it.joinToString("") }
        .forEach { if (it.isPalindrome()) palindromes.add(it) }
    return palindromes.toList()
}
fun String.extractInts(delimiter: String = ""): List<Int> = this.split(delimiter).mapNotNull { it.toIntOrNull() }

fun String.isNumber() = this.toIntOrNull() != null
