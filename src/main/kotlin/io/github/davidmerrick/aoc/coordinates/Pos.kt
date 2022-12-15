package io.github.davidmerrick.aoc.coordinates

import kotlin.math.abs

data class Pos(val x: Int, val y: Int) : Comparable<Pos> {

    override fun compareTo(other: Pos): Int {
        return (this.x + this.y).compareTo(other.x + other.y)
    }

    companion object {
        /**
         * Parses a Pos from a comma-separated string
         * i.e. "1,2"
         */
        fun of(str: String) = str.split(",")
            .map { it.toInt() }
            .let { Pos(it.first(), it.last()) }
    }
}

fun Pos.manhattanDistance(other: Pos) = abs(this.x - other.x) + abs(this.y - other.y)
fun Pos.chebyshevDistance(other: Pos) = maxOf(abs(this.x - other.x), abs(this.y - other.y))

operator fun Pos.plus(move: Move) = Pos(this.x + move.dx, this.y + move.dy)
