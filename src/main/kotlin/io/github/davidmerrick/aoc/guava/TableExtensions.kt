package io.github.davidmerrick.aoc.guava

import com.google.common.collect.HashBasedTable
import io.github.davidmerrick.aoc.coordinates.Pos
import io.github.davidmerrick.aoc.coordinates.manhattanDistance

fun <R, C, V> HashBasedTable<R, C, V>.rowList(row: R): List<V> {
    require(this.rowMap().containsKey(row))
    return this.rowMap()[row]!!.map { it.value }.toList()
}

fun <R, C, V> HashBasedTable<R, C, V>.columnList(column: C): List<V> {
    require(this.columnMap().containsKey(column))
    return this.columnMap()[column]!!.map { it.value }.toList()
}

fun <V> HashBasedTable<Int, Int, V>.fill(width: Int, height: Int, value: V) {
    for (row in 0 until height) {
        for (column in 0 until width) {
            this.put(row, column, value)
        }
    }
}

/**
 * Behaves similarly to Kotlin's Map.compute().
 * Iterates a range of cells from the starting coords (row, column) to
 * the dimensions (width, height) and sets the value in each cell to the one
 * specified by the computeValue() function.
 */
fun <V> HashBasedTable<Int, Int, V>.compute(
    coords: Pair<Int, Int>,
    dimensions: Pair<Int, Int>,
    computeValue: (V?) -> V
) {
    for (row in coords.first until coords.first + dimensions.first) {
        for (column in coords.second until coords.second + dimensions.second) {
            computeValue(this.get(row, column))
                .let { this.put(row, column, it) }
        }
    }
}

fun <R, C, V> HashBasedTable<R, C, V>.print(valueTransform: (V) -> String = { it.toString() }) = buildString {
    rowMap().map { it.value }.forEach { row ->
        row.map { valueTransform(it.value) }.forEach { append(it) }
        append("\n")
    }
}

fun <R, C, V> HashBasedTable<R, C, V>.printIndexed(valueTransform: (R, C, V) -> String) = buildString {
    rowMap().forEach { row ->
        row.value
            .map { valueTransform(row.key, it.key, it.value) }.forEach { append(it) }
        append("\n")
    }
}

fun <V> HashBasedTable<Int, Int, V>.getNeighbors(
    row: Int,
    column: Int,
    includeDiagonals: Boolean = false
): List<TableEntry<Int, Int, V>> {
    return buildList {
        for (r in row - 1..row + 1) {
            for (c in column - 1..column + 1) {
                if (Pos(r, c).manhattanDistance(Pos(row, column)) > 1 && !includeDiagonals) continue
                if (r == row && c == column) continue
                getEntry(r, c)?.let { this.add(it) }
            }
        }
    }
}

fun <V> HashBasedTable<Int, Int, V>.getNeighbors(
    pos: Pos,
    includeDiagonals: Boolean = false
) = getNeighbors(pos.y, pos.x, includeDiagonals)

fun <R, C, V> HashBasedTable<R, C, V>.getEntry(row: R, column: C): TableEntry<R, C, V>? {
    return this.get(row, column)?.let {
        TableEntry(row, column, it)
    }
}

fun <R, C, V> HashBasedTable<R, C, V>.asSequence(): Sequence<TableEntry<R, C, V>> {
    return this.rowMap().asSequence()
        .flatMap { row ->
            row.value.asSequence().map { column ->
                TableEntry(row.key, column.key, column.value)
            }
        }
}

/**
 * Returns a list of values given a row and a column range to search
 * rangeStart is inclusive; rangeEnd is exclusive
 */
fun <V> HashBasedTable<Int, Int, V>.columnRange(
    row: Int,
    rangeStart: Int,
    rangeEnd: Int
): List<V> {
    val table = this
    return buildList {
        for (column in rangeStart until rangeEnd) {
            add(table.get(row, column)!!)
        }
    }
}

/**
 * Returns a list of values given a column and a row range to search
 * rangeStart is inclusive; rangeEnd is exclusive
 */
fun <V> HashBasedTable<Int, Int, V>.rowRange(
    column: Int,
    rangeStart: Int,
    rangeEnd: Int
): List<V> {
    val table = this
    return buildList {
        for (row in rangeStart until rangeEnd) {
            add(table.get(row, column)!!)
        }
    }
}

fun <V> parseTable(input: List<List<V>>): HashBasedTable<Int, Int, V> {
    val table = HashBasedTable.create<Int, Int, V>()
    for (row in input.indices) {
        for (column in input[0].indices) {
            table.put(row, column, input[row][column])
        }
    }
    return table
}

class TableEntry<R, C, V>(
    val row: R,
    val column: C,
    val value: V
)
