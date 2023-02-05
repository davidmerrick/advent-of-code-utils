package io.github.davidmerrick.aoc.coordinates

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun rotateClockwise(n: Int = 1): Direction {
        require(n > 0)
        return values()[(this.ordinal + n) % values().size]
    }

    fun rotateCounterClockwise(n: Int = 1): Direction {
        require(n > 0)
        return values()[(this.ordinal - n).mod(values().size)]
    }
}

