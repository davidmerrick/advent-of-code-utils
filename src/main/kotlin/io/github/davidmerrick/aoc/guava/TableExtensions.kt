package io.github.davidmerrick.aoc.guava

import com.google.common.collect.HashBasedTable
import io.github.davidmerrick.aoc.coordinates.Pos
import io.github.davidmerrick.aoc.coordinates.Range
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

fun <V> HashBasedTable<Int, Int, V>.fill(start: Pos, end: Pos, value: V) {
    for (row in start.y..end.y) {
        for (column in start.x..end.x) {
            this.put(row, column, value)
        }
    }
}

/**
 * Only fills empty cells in the range
 */
fun <V> HashBasedTable<Int, Int, V>.fillEmpty(start: Pos, end: Pos, value: V) {
    val minX = minOf(start.x, end.x)
    val minY = minOf(start.y, end.y)
    val maxX = maxOf(start.x, end.x)
    val maxY = maxOf(start.y, end.y)

    for (row in minY..maxY) {
        for (column in minX..maxX) {
            lookup(row, column) ?: this.put(row, column, value)
        }
    }
}

fun <R, C, V> HashBasedTable<R, C, V>.lookup(row: R, column: C): V? {
    return if (this.contains(row, column)) {
        this.get(row, column)
    } else null
}

fun <V> HashBasedTable<Int, Int, V>.lookup(pos: Pos) = lookup(pos.y, pos.x)

fun <V> HashBasedTable<Int, Int, V>.fill(range: Range, value: V) = fill(range.start, range.end, value)

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

fun <V> HashBasedTable<Int, Int, V>.getEntry(pos: Pos): TableEntry<Int, Int, V>? {
    return this.getEntry(pos.y, pos.x)
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

data class TableEntry<R, C, V>(
    val row: R,
    val column: C,
    val value: V
)

fun <V> TableEntry<Int, Int, V>.pos() = Pos(this.column, this.row)

data class Path<V>(
    val steps: List<TableEntry<Int, Int, V>> = listOf()
) {
    val length = steps.size - 1

    /**
     * Makes a copy of the path with the new step at the end
     */
    fun withStep(step: TableEntry<Int, Int, V>): Path<V> {
        val newSteps = steps.toMutableList()
        newSteps.add(step)
        return this.copy(steps = newSteps)
    }
}

fun <V> HashBasedTable<Int, Int, V>.getAdjacent(pos: Pos, adjacentIf: (V, V) -> Boolean): Set<Pos> {
    val table = this
    val value = table.get(pos.y, pos.x)!!
    return buildSet {
        table.getNeighbors(pos).forEach {
            if (adjacentIf(value, it.value)) {
                add(Pos(it.column, it.row))
            }
        }
    }
}

fun <V> HashBasedTable<Int, Int, V>.put(pos: Pos, value: V) = this.put(pos.y, pos.x, value)

/**
 * Uses breadth-first search on a table to find the shortest path between
 * two table entries matching a predicate.
 * isAdjacent() is a function which tests a table entry (first param) and its neighbor (second param)
 * to determine whether it should be adjacent
 */
fun <V> HashBasedTable<Int, Int, V>.shortestPath(
    sourcePredicate: (V) -> Boolean,
    destinationPredicate: (V) -> Boolean,
    isAdjacent: (V, V) -> Boolean
): Int {
    val visited = mutableSetOf<Pos>()
    val start = this.asSequence().first { sourcePredicate(it.value) }
        .pos()
        .let { Step(it, 0) }

    val queue = ArrayDeque<Step>().apply { add(start) }
    visited.add(start.pos)

    while (queue.isNotEmpty()) {
        val step = queue.removeFirst()

        if (destinationPredicate(this.getEntry(step.pos)!!.value)) {
            return step.distance
        } else {
            this.getAdjacent(step.pos, isAdjacent)
                .filterNot { it in visited }
                .map { step.toward(it) }
                .forEach {
                    queue.addLast(it)
                    visited.add(it.pos)
                }
        }
    }

    error("No path found matching predicate")
}

data class Step(
    val pos: Pos, val
    distance: Int
) {
    fun toward(pos: Pos) = this.copy(pos = pos, distance = distance + 1)
}
