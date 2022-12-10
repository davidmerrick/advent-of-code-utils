package io.github.davidmerrick.aoc.guava

import com.google.common.collect.HashBasedTable

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
