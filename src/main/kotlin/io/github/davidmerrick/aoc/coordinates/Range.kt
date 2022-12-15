package io.github.davidmerrick.aoc.coordinates

/**
 * Defines a range of positions
 * from start (inclusive) to end (inclusive)
 */
data class Range(val start: Pos, val end: Pos) {
    val positions by lazy {
        buildList {
            for (x in start.x..end.x) {
                for (y in start.y..end.y) {
                    add(Pos(x, y))
                }
            }
        }
    }
}
