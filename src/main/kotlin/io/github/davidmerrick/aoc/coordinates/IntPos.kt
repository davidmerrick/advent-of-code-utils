package io.github.davidmerrick.aoc.coordinates

import kotlin.math.abs

data class IntPos(override val x: Int, override val y: Int) : Pos<Int> {

    override fun compareTo(other: Pos<Int>): Int {
        return (this.x + this.y).compareTo(other.x + other.y)
    }

    /**
     * Draw a line to another pos
     * and return all the points in between.
     * List includes the start and end points.
     */
    fun lineTo(other: IntPos): List<IntPos> {
        require(this != other)
        require(this.x != other.x)

        val start = listOf(this, other).minBy { it.x.toLong() }
        val end = listOf(this, other).maxBy { it.x.toLong() }
        val slope = (start.y - end.y) / (start.x - end.x)
        return buildList {
            var curPos = start
            while (curPos.x < end.x) {
                add(curPos)
                curPos = curPos.copy(x = curPos.x + 1, y = curPos.y + slope)
            }
            add(end)
        }
    }

    companion object {
        /**
         * Parses a Pos from a comma-separated string
         * i.e. "1,2"
         */
        fun of(str: String) = str.split(",")
            .map { it.toInt() }
            .let { IntPos(it.first(), it.last()) }
    }

    override fun manhattanDistance(other: Pos<Int>): Int {
        return abs(this.x - other.x) + abs(this.y - other.y)
    }

    override fun chebyshevDistance(other: Pos<Int>): Int {
        return maxOf(abs(this.x - other.x), abs(this.y - other.y))
    }
}

operator fun IntPos.plus(move: Move) = IntPos(this.x + move.dx, this.y + move.dy)
operator fun IntPos.plus(other: IntPos) = IntPos(this.x + other.x, this.y + other.y)
operator fun IntPos.minus(other: IntPos) = IntPos(this.x - other.x, this.y - other.y)

fun generatePositions(xRange: IntRange, yRange: IntRange): List<IntPos> {
    return buildList {
        for (x in xRange) {
            for (y in yRange) {
                add(IntPos(x, y))
            }
        }
    }
}

fun generatePositions(x: Int, yRange: IntRange) = generatePositions(x..x, yRange)
fun generatePositions(xRange: IntRange, y: Int) = generatePositions(xRange, y..y)
