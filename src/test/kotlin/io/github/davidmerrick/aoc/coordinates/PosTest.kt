package io.github.davidmerrick.aoc.coordinates

import io.kotlintest.fail
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class PosTest {

    @Test
    fun `Draw line`() {
        val line = Pos(2, 2).lineTo(Pos(4, 4))
        line.size shouldBe 3
        line.shouldContainAll(Pos(2, 2), Pos(3, 3), Pos(4, 4))
    }

    @Test
    fun `Draw vertical line`() {
        // For now, this method throws an exception if line is vertical
        try {
            Pos(2, 2).lineTo(Pos(2, 4))
            fail("Should have thrown an exception")
        } catch (e: Exception){ }
    }
}
