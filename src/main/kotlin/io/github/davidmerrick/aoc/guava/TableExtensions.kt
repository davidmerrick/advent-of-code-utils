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
