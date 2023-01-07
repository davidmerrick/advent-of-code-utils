package io.github.davidmerrick.aoc.guava

import com.google.common.collect.HashBasedTable
import io.github.davidmerrick.aoc.collections.toCharRows
import io.github.davidmerrick.aoc.collections.toIntRows
import io.kotlintest.matchers.collections.shouldContainAll
import io.kotlintest.matchers.collections.shouldContainInOrder
import io.kotlintest.should
import io.kotlintest.shouldBe
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

    @Test
    fun `Get neighbors`(){
        val table = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent()
            .lines()
            .toCharRows()
            .let { parseTable(it) }

        val neighbors = table.getNeighbors(2, 1)
        neighbors.size shouldBe 4
        neighbors.map { it.value } shouldContainAll listOf('b', 'a', 'c', 'c')
    }

    @Test
    fun `Get neighbors at an edge`(){
        val table = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.trimIndent()
            .lines()
            .toCharRows()
            .let { parseTable(it) }

        val neighbors = table.getNeighbors(0, 1)
        neighbors.size shouldBe 3
        neighbors.map { it.value } shouldContainAll listOf('S', 'b', 'b')
    }

    @Test
    fun `Get with default`(){
        val table = HashBasedTable.create<Int, Int, String>()
        table.get(1, 2, "foo") shouldBe "foo"
    }
}
