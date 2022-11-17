package io.github.davidmerrick.aoc

import io.github.davidmerrick.aoc.util.isPalindrome
import io.kotlintest.shouldBe
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class StringExtensionsTest {

    @TestFactory
    fun `isPalindrome test`() = listOf(
        "abba" to true,
        "abbcd" to false
    ).map { (str, expected) ->
        dynamicTest("Is palindrome test") {
            str.isPalindrome() shouldBe expected
        }
    }
}