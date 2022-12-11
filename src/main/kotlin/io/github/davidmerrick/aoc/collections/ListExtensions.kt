package io.github.davidmerrick.aoc.collections

/**
 * Finds the min value in a list matching the selector
 * Returns all list items matching that value
 */
fun <T, R : Comparable<R>> List<T>.filterMinBy(selector: (T) -> R): List<T> {
    val minValue = this.minOf { selector(it) }
    return this.filter { selector(it) == minValue }
}
