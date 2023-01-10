package io.github.davidmerrick.aoc.coordinates

import io.kotlintest.fail
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

internal class PosTest {

    @Test
    fun `Draw line`() {
        val line = IntPos(2, 2).lineTo(IntPos(4, 4))
        line.size shouldBe 3
        line.shouldContainAll(IntPos(2, 2), IntPos(3, 3), IntPos(4, 4))
    }

    @Test
    fun `Draw vertical line`() {
        // For now, this method throws an exception if line is vertical
        try {
            IntPos(2, 2).lineTo(IntPos(2, 4))
            fail("Should have thrown an exception")
        } catch (e: Exception){ }
    }
}
