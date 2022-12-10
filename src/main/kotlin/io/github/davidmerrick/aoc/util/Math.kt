package io.github.davidmerrick.aoc.util

import kotlin.math.abs

fun Pair<Int, Int>.manhattanDistanceTo(other: Pair<Int, Int>): Int {
    return abs(this.first - other.first) + abs(this.second - other.second)
}

fun Collection<Int>.product() = this.reduce { a, b -> a * b }
fun Sequence<Int>.product() = this.reduce { a, b -> a * b }
