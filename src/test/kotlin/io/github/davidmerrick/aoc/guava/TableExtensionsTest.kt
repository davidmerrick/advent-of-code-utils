package io.github.davidmerrick.aoc.guava

import com.google.common.collect.HashBasedTable
import io.kotlintest.matchers.collections.shouldContainInOrder
import org.junit.jupiter.api.Test

class TableExtensionsTest {

    @Test
    fun `List row`() {
        val table = HashBasedTable.create<Int, Int, String>()
        table.put(0, 0, "foo")
        table.put(0, 1, "bar")
        table.put(0, 2, "baz")
        table.rowList(0) shouldContainInOrder listOf("foo", "bar", "baz")
    }

    @Test
    fun `List column`() {
        val table = HashBasedTable.create<Int, Int, String>()
        table.put(0, 0, "foo")
        table.put(1, 0, "bar")
        table.put(2, 0, "baz")
        table.columnList(0) shouldContainInOrder listOf("foo", "bar", "baz")
    }
}
