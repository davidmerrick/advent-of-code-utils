package io.github.davidmerrick.aoc.coordinates

/**
 * Defines a range of positions
 * from start (inclusive) to end (inclusive)
 */
data class Range(val start: Pos, val end: Pos) {
    private val minX = minOf(start.x, end.x)
    private val minY = minOf(start.y, end.y)

    private val maxX = maxOf(start.x, end.x)
    private val maxY = maxOf(start.y, end.y)

    val positions by lazy {
        buildList {
            for (x in minX..maxX) {
                for (y in minY..maxY) {
                    add(Pos(x, y))
                }
            }
        }
    }
}
