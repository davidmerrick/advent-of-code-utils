package io.github.davidmerrick.aoc.util

fun String.wordCount(delimiter: String = " "): Map<String, Int> =
    this.split(delimiter).groupingBy { it }.eachCount()

fun String.charCount(): Map<Char, Int> =
    this.groupingBy { it }.eachCount()

fun String.charsToPosition(): Map<Int, Char> =
    this.mapIndexed { i, char -> i to char }
        .toMap()

/**
 * Takes a sequence of lines
 * and maps them into columns
 */
fun Sequence<String>.columns() = this
    .flatMap { it.charsToPosition().entries }
    .groupBy({ it.key }, { it.value })
    .mapValues { it.value.joinToString("") }

fun String.mostCommonChar(): Char = this.charCount()
    .maxBy { it.value }
    .key

fun String.leastCommonChar(): Char = this.charCount()
    .minBy { it.value }
    .key

fun <T : Any> Sequence<String>.mapColumns(transform: (String) -> T) =
    this.columns()
        .map { transform(it.value) }
        .joinToString("")

fun String.isPalindrome(): Boolean = this.reversed() == this

fun String.getPalindromes(length: Int): List<String> {
    val palindromes = mutableListOf<String>()
    this.asSequence()
        .windowed(length)
        .map { it.joinToString("") }
        .forEach { if (it.isPalindrome()) palindromes.add(it) }
    return palindromes.toList()
}

public inline fun Iterable<String>.filterNotEmpty(): List<String> {
    return filterNotTo(ArrayList()) { it.isEmpty() }
}
