package io.github.davidmerrick.aoc.coordinates

/**
 * Defines a range of positions
 * from start (inclusive) to end (inclusive)
 */
data class Range(val start: Pos, val end: Pos) {
    val minX = minOf(start.x, end.x)
    val minY = minOf(start.y, end.y)

    val maxX = maxOf(start.x, end.x)
    val maxY = maxOf(start.y, end.y)

    /**
     * Todo: Find out how to not use an intermediate list
     * Returns all positions if this range were made into a rectangle
     */
    fun boxify(
        minX: Int = this.minX,
        maxX: Int = this.maxX,
        minY: Int = this.minY,
        maxY: Int = this.maxY
    ): Sequence<Pos> {
        return buildList {
            for (x in minX..maxX) {
                for (y in minY..maxY) {
                    add(Pos(x, y))
                }
            }
        }.asSequence()
    }

    companion object {
        fun of(positions: Collection<Pos>): Range {
            return Range(
                Pos(
                    positions.minOf { it.x },
                    positions.minOf { it.y },
                ),
                Pos(
                    positions.maxOf { it.x },
                    positions.maxOf { it.y },
                )
            )
        }
    }
}
