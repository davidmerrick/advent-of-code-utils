package io.github.davidmerrick.aoc.util

import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class MathTest {

    @Test
    fun `Manhattan distance test`() {
        (1 to 1).manhattanDistanceTo(0 to 0) shouldBe 2
        (-5 to 5).manhattanDistanceTo(5 to -5) shouldBe 20
        (5 to 0).manhattanDistanceTo(5 to 5) shouldBe 5
    }

    @Test
    fun `Product test`() {
        listOf(1, 2, 3).product() shouldBe 6
    }
}
