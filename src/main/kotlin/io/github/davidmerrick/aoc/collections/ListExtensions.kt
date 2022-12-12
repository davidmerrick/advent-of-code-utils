package io.github.davidmerrick.aoc.collections

import io.github.davidmerrick.aoc.util.charsToPosition
import io.github.davidmerrick.aoc.util.extractInts

/**
 * Finds the min value in a list matching the selector
 * Returns all list items matching that value
 */
fun <T, R : Comparable<R>> List<T>.filterMinBy(selector: (T) -> R): List<T> {
    val minValue = this.minOf { selector(it) }
    return this.filter { selector(it) == minValue }
}

fun List<String>.toIntRows(): List<List<Int>> = this.map(String::extractInts)
fun List<String>.toCharRows(): List<List<Char>> = this.map { it.toList() }

/**
 * Takes a sequence of lines
 * and maps them into columns
 */
fun Sequence<String>.columns() = this
    .flatMap { it.charsToPosition().entries }
    .groupBy({ it.key }, { it.value })
    .mapValues { it.value.joinToString("") }

fun <T : Any> Sequence<String>.mapColumns(transform: (String) -> T) =
    this.columns()
        .map { transform(it.value) }
        .joinToString("")

inline fun Iterable<String>.filterNotEmpty() = filterNotTo(ArrayList(), String::isEmpty)
