package io.github.davidmerrick.aoc.coordinates

interface Pos<T : Number> : Comparable<Pos<T>> {
    val x: T
    val y: T

    fun manhattanDistance(other: Pos<T>): T
    fun chebyshevDistance(other: Pos<T>): T
}
