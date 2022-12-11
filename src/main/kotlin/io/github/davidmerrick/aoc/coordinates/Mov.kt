package io.github.davidmerrick.aoc.coordinates

import kotlin.math.abs

data class Move(val dx: Int, val dy: Int)

fun Move.manhattanDistance() = abs(dx) + abs(dy)
