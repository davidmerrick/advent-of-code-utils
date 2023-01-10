package io.github.davidmerrick.aoc.coordinates

import kotlin.math.abs

data class LongPos(override val x: Long, override val y: Long) : Pos<Long> {

    override fun compareTo(other: Pos<Long>): Int {
        return (this.x + this.y).compareTo(other.x + other.y)
    }

    override fun manhattanDistance(other: Pos<Long>): Long {
        return abs(this.x - other.x) + abs(this.y - other.y)
    }

    override fun chebyshevDistance(other: Pos<Long>): Long {
        return maxOf(abs(this.x - other.x), abs(this.y - other.y))
    }

    companion object {
        /**
         * Parses a Pos from a comma-separated string
         * i.e. "1,2"
         */
        fun of(str: String) = str.split(",")
            .map { it.toLong() }
            .let { LongPos(it.first(), it.last()) }
    }
}

operator fun LongPos.plus(other: LongPos) = LongPos(this.x + other.x, this.y + other.y)
