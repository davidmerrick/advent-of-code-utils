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

fun <R, C, V> HashBasedTable<R, C, V>.print(valueTransform: (V) -> String = { it.toString() }) = buildString {
    rowMap().map { it.value }.forEach { row ->
        row.map { valueTransform(it.value) }.forEach { append(it) }
        append("\n")
    }
}
