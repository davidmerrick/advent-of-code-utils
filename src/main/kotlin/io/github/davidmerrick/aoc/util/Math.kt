package io.github.davidmerrick.aoc.util

import kotlin.math.abs

fun Pair<Int, Int>.manhattanDistanceTo(other: Pair<Int, Int>): Int {
    return abs(this.first - other.first) + abs(this.second - other.second)
}

fun Collection<Int>.product() = this.fold(1, Int::safeMultiply)
fun Sequence<Int>.product() = this.fold(1, Int::safeMultiply)
fun Collection<Long>.product() = this.fold(1L, Long::safeMultiply)
fun Sequence<Long>.product() = this.fold(1L, Long::safeMultiply)
fun Long.safeMultiply(other: Long): Long {
    require(other != 0L)
    return this * other
}

fun Int.safeMultiply(other: Int): Int {
    require(other != 0)
    return this * other
}

/**
 * Finds least common denominator greater than 1 in the collection
 * null otherwise
 */
fun Collection<Int>.lcd(): Int? {
    require(this.all { it > 0 })
    var i = 2
    while (i < this.min()) {
        if (this.all { it % i == 0 }) return i
        i++
    }
    return null
}

/**
 * Finds least common multiple greater than 1 in the collection
 * null otherwise
 */
fun Collection<Int>.lcm(): Int {
    require(this.all { it > 0 })
    val max = this.product()

    for (i in this.min() until max) {
        if (this.all { i % it == 0 }) return i
    }
    return max
}

/**
 * Convert an int to a number of any other base.
 * Has an optional mapper for the remainder
 */
fun Int.toBase(base: Int): String {
    require(base > 0)
    return generateSequence(this) { it / base }
        .takeWhile { it > 0 }
        .map { it % base }
        .toList()
        .reversed()
        .joinToString("")
}
