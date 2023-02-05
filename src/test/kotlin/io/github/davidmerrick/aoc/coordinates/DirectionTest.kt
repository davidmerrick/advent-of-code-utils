package io.github.davidmerrick.aoc.coordinates

import io.github.davidmerrick.aoc.coordinates.Direction.EAST
import io.github.davidmerrick.aoc.coordinates.Direction.NORTH
import io.github.davidmerrick.aoc.coordinates.Direction.SOUTH
import io.github.davidmerrick.aoc.coordinates.Direction.WEST
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class DirectionTest {
    @Test
    fun `Rotate clockwise test`() {
        EAST.rotateClockwise() shouldBe SOUTH
        EAST.rotateClockwise(4) shouldBe EAST
        WEST.rotateClockwise() shouldBe NORTH
    }

    @Test
    fun `Rotate counterclockwise test`() {
        SOUTH.rotateCounterClockwise() shouldBe EAST
        EAST.rotateCounterClockwise(4) shouldBe EAST
        NORTH.rotateCounterClockwise() shouldBe WEST
    }
}
