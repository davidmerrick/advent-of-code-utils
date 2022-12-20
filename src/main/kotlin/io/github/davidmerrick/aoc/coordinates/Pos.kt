package io.github.davidmerrick.aoc.coordinates

import kotlin.math.abs

data class Pos(val x: Int, val y: Int) : Comparable<Pos> {

    override fun compareTo(other: Pos): Int {
        return (this.x + this.y).compareTo(other.x + other.y)
    }

    /**
     * Draw a line to another pos
     * and return all the points in between.
     * List includes the start and end points.
     */
    fun lineTo(other: Pos): List<Pos> {
        require(this != other)
        require(this.x != other.x)

        val start = listOf(this, other).minBy { it.x }
        val end = listOf(this, other).maxBy { it.x }
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
            .let { Pos(it.first(), it.last()) }
    }
}

fun Pos.manhattanDistance(other: Pos) = abs(this.x - other.x) + abs(this.y - other.y)
fun Pos.chebyshevDistance(other: Pos) = maxOf(abs(this.x - other.x), abs(this.y - other.y))

operator fun Pos.plus(move: Move) = Pos(this.x + move.dx, this.y + move.dy)
