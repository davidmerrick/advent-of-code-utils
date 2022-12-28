package io.github.davidmerrick.aoc.util

import io.kotlintest.shouldBe
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

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

    @TestFactory
    fun `Convert base 5`() = listOf(
        3 to "3",
        5 to "10",
        10 to "20",
        13 to "23",
    ).map { (input, expected) ->
        dynamicTest("Convert $input to base 5") {
            input.toBase(5) shouldBe expected
        }
    }
}
