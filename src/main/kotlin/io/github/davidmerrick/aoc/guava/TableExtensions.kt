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
