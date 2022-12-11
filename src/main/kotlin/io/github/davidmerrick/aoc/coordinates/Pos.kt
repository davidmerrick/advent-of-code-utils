package io.github.davidmerrick.aoc.coordinates

import kotlin.math.abs

data class Pos(val x: Int, val y: Int)

fun Pos.manhattanDistance(other: Pos) = abs(this.x - other.x) + abs(this.y - other.y)
fun Pos.chebyshevDistance(other: Pos) = maxOf(abs(this.x - other.x), abs(this.y - other.y))

operator fun Pos.plus(move: Move) = Pos(this.x + move.dx, this.y + move.dy)
